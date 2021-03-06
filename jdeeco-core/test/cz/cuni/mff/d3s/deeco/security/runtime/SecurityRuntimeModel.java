package cz.cuni.mff.d3s.deeco.security.runtime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.security.KeyStoreException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.mockito.Mockito;

import cz.cuni.mff.d3s.deeco.annotations.Allow;
import cz.cuni.mff.d3s.deeco.annotations.Component;
import cz.cuni.mff.d3s.deeco.annotations.Ensemble;
import cz.cuni.mff.d3s.deeco.annotations.HasSecurityRole;
import cz.cuni.mff.d3s.deeco.annotations.In;
import cz.cuni.mff.d3s.deeco.annotations.InOut;
import cz.cuni.mff.d3s.deeco.annotations.KnowledgeExchange;
import cz.cuni.mff.d3s.deeco.annotations.Local;
import cz.cuni.mff.d3s.deeco.annotations.Membership;
import cz.cuni.mff.d3s.deeco.annotations.Out;
import cz.cuni.mff.d3s.deeco.annotations.PeriodicScheduling;
import cz.cuni.mff.d3s.deeco.annotations.Process;
import cz.cuni.mff.d3s.deeco.annotations.Rating;
import cz.cuni.mff.d3s.deeco.annotations.RatingsProcess;
import cz.cuni.mff.d3s.deeco.annotations.SecurityRoleDefinition;
import cz.cuni.mff.d3s.deeco.annotations.SecurityRoleParam;
import cz.cuni.mff.d3s.deeco.annotations.processor.AnnotationProcessor;
import cz.cuni.mff.d3s.deeco.annotations.processor.AnnotationProcessorException;
import cz.cuni.mff.d3s.deeco.executor.Executor;
import cz.cuni.mff.d3s.deeco.executor.SameThreadExecutor;
import cz.cuni.mff.d3s.deeco.integrity.PathRating;
import cz.cuni.mff.d3s.deeco.integrity.RatingsHolder;
import cz.cuni.mff.d3s.deeco.integrity.RatingsManager;
import cz.cuni.mff.d3s.deeco.integrity.RatingsManagerImpl;
import cz.cuni.mff.d3s.deeco.integrity.ReadonlyRatingsHolder;
import cz.cuni.mff.d3s.deeco.knowledge.CloningKnowledgeManagerFactory;
import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeManagerContainer;
import cz.cuni.mff.d3s.deeco.model.runtime.api.ComponentInstance;
import cz.cuni.mff.d3s.deeco.model.runtime.api.ContextKind;
import cz.cuni.mff.d3s.deeco.model.runtime.api.RuntimeMetadata;
import cz.cuni.mff.d3s.deeco.model.runtime.api.Trigger;
import cz.cuni.mff.d3s.deeco.model.runtime.custom.RuntimeMetadataFactoryExt;
import cz.cuni.mff.d3s.deeco.network.DataSender;
import cz.cuni.mff.d3s.deeco.runtime.DEECoContainer;
import cz.cuni.mff.d3s.deeco.runtime.DEECoNode;
import cz.cuni.mff.d3s.deeco.runtime.DuplicateEnsembleDefinitionException;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeFrameworkImpl;
import cz.cuni.mff.d3s.deeco.runtimelog.RuntimeLogger;
import cz.cuni.mff.d3s.deeco.scheduler.NoExecutorAvailableException;
import cz.cuni.mff.d3s.deeco.scheduler.Scheduler;
import cz.cuni.mff.d3s.deeco.scheduler.SingleThreadedScheduler;
import cz.cuni.mff.d3s.deeco.security.SecurityHelper;
import cz.cuni.mff.d3s.deeco.security.SecurityKeyManager;
import cz.cuni.mff.d3s.deeco.security.SecurityKeyManagerImpl;
import cz.cuni.mff.d3s.deeco.task.EnsembleTask;
import cz.cuni.mff.d3s.deeco.task.ParamHolder;
import cz.cuni.mff.d3s.deeco.task.Task;
import cz.cuni.mff.d3s.deeco.task.TaskInvocationException;
import cz.cuni.mff.d3s.deeco.timer.DiscreteEventTimer;

/**
 * 
 * @author Ondřej Štumpf
 *
 */
public class SecurityRuntimeModel {
	
	@SecurityRoleDefinition(aliasedBy = PoliceInAuthorsCity.class)
	public static interface PoliceInCity {
		@SecurityRoleParam
		public static final String cityId = "[cityId]";
	}
	
	@SecurityRoleDefinition
	public static interface PoliceEverywhere extends PoliceInCity {
		@SecurityRoleParam
		public static final String cityId = null;
	}
	
	@SecurityRoleDefinition
	public static interface PoliceInAuthorsCity extends PoliceEverywhere {
		@SecurityRoleParam(ContextKind.SHADOW)
		public static final String cityId = "[cityId]";
	}
	
	@Component 
	public static class VehicleComponent  {
		public String id;
		public String cityId;
		
		@Allow(PoliceInCity.class)
		public String secret;
		
		@Allow(PoliceEverywhere.class)		
		public String secret_for_city;
		
		public VehicleComponent(String id, String cityId, String secret, String secret_for_city) {
			this.id = id;		
			this.cityId = cityId;
			this.secret = secret;
			this.secret_for_city = secret_for_city;			
		}
	}
	
	@Component 
	@HasSecurityRole(PoliceInCity.class)
	public static class PoliceComponent  {
		public String id;
		public String cityId;
		
		@Local
		public Map<String, String> secrets;
		
		@Local
		public String secret_for_city;
		
		public PoliceComponent(String id, String cityId) {
			this.id = id;		
			this.cityId = cityId;
			this.secrets = new HashMap<>();
		}
		
		@Process
		@PeriodicScheduling(period=1000)
		public static void process(@In("cityId") String cityId, @Rating("cityId") ReadonlyRatingsHolder holder) {
			
		}
		
		@RatingsProcess
		public static void ratingProcess(@In("cityId") String cityId, @Rating("cityId") RatingsHolder holder) {
			if (cityId.equals("Prague")) {
				holder.setMyRating(PathRating.UNUSUAL);
			} else {
				holder.setMyRating(PathRating.OK);
			}
		}
	}
	
	@Component 
	@HasSecurityRole(PoliceEverywhere.class)		
	public static class GlobalPoliceComponent  {
		public String id;
		
		@Local
		public Map<String, String> secrets;
		
		@Allow(PoliceInAuthorsCity.class)
		public String secret_for_city;
		
		public GlobalPoliceComponent(String id) {
			this.id = id;		
			this.secrets = new HashMap<>();
		}
	}
	
	@Ensemble
	@PeriodicScheduling(period = 1000)
	public static class AllEnsemble {
		
		public static Predicate<String> membership;
		
		@Membership
		public static boolean membership(@In("member.id") String id) {
			return membership.test(id);
		}

		@KnowledgeExchange
		public static void exchange(@In("member.id") String id, @In("member.secret") String secret, 
				 @InOut("coord.secrets") ParamHolder<Map<String, String>> secrets) {
			secrets.value.put(id, secret);		
		}
	}

	@Ensemble
	@PeriodicScheduling(period = 1000)
	public static class PoliceEverywhereEnsemble {
		
		public static BiFunction<String, String, Boolean> membership;
		
		@Membership
		public static boolean membership(@In("member.id") String memberId, @In("coord.id") String coordId) {
			return membership.apply(memberId, coordId);			
		}

		@KnowledgeExchange
		public static void exchange(@In("member.secret_for_city") String member_secret_for_city, @Out("coord.secret_for_city") ParamHolder<String> coord_secret_for_city) {
			coord_secret_for_city.value = member_secret_for_city + " modified";
		}
	}
	
	protected DEECoContainer deecoContainer;
	
	public RuntimeMetadata model;
	public AnnotationProcessor processor;
	
	public DataSender dataSender;
	public SecurityKeyManager securityKeyManager;
	
	public Scheduler scheduler;
	
	public Executor executor;
	
	public KnowledgeManagerContainer container;
	public VehicleComponent vehicleComponent;
	public PoliceComponent policeComponent1, policeComponent2;
	public GlobalPoliceComponent globalPoliceComponent;
	
	public RuntimeFrameworkImpl runtime;
	public SecurityHelper securityHelper;
	public RuntimeLogger runtimeLogger;
	public RatingsManager ratingsManager;
	
	public SecurityRuntimeModel(DEECoContainer deecoContainer) throws KeyStoreException, AnnotationProcessorException, DuplicateEnsembleDefinitionException, NoExecutorAvailableException {
		this.deecoContainer = deecoContainer;
		
		securityKeyManager = SecurityKeyManagerImpl.getInstance();
		executor = new SameThreadExecutor();
		DiscreteEventTimer simulation = new DiscreteEventTimer();
		scheduler = new SingleThreadedScheduler(executor, simulation, Mockito.mock(DEECoNode.class));
		securityHelper = new SecurityHelper();
		
		model = RuntimeMetadataFactoryExt.eINSTANCE.createRuntimeMetadata();
		processor = new AnnotationProcessor(RuntimeMetadataFactoryExt.eINSTANCE, model, new CloningKnowledgeManagerFactory());
		dataSender = mock(DataSender.class);
		
		vehicleComponent = new VehicleComponent("V1", "Prague", "top secret", "city secret");
		policeComponent1 = new PoliceComponent("P1", "Prague");
		policeComponent2 = new PoliceComponent("P2", "Pilsen");
		globalPoliceComponent = new GlobalPoliceComponent("G1");
		
		processor.processComponent(vehicleComponent);
		processor.processComponent(policeComponent1);
		processor.processComponent(policeComponent2);
		processor.processComponent(globalPoliceComponent);
		processor.processEnsemble(AllEnsemble.class);
		processor.processEnsemble(PoliceEverywhereEnsemble.class);
		
		// set ensemble to allow all components
		AllEnsemble.membership = id -> true;
		PoliceEverywhereEnsemble.membership = (memberId, coordId) -> (memberId.equals("V1") && coordId.equals("G1")) || (memberId.equals("G1") && coordId.equals("P1"));
		
		container = spy(new KnowledgeManagerContainer(new CloningKnowledgeManagerFactory(), model));
		ratingsManager = RatingsManagerImpl.getInstance();
		runtime = spy(new RuntimeFrameworkImpl(model, scheduler, executor, container, runtimeLogger, ratingsManager));
		runtime.init(deecoContainer);	
	}
	
	public void invokeEnsembleTasks() throws TaskInvocationException {
		// manually invoke ensemble knowledge exchange
		
		invokeAllEnsemble();
		invokePoliceEverywhereEnsemble();
	}
	
	public void invokeAllEnsemble() throws TaskInvocationException {
		Trigger trigger = model.getEnsembleDefinitions().get(0).getTriggers().get(0);
		
		for (ComponentInstance ci : model.getComponentInstances()) {
			Task task = new EnsembleTask(ci.getEnsembleControllers().get(0), scheduler, container, ratingsManager);
			((EnsembleTask) task).init(deecoContainer);
			task.invoke(trigger);
		}
	}
	
	public void invokePoliceEverywhereEnsemble() throws TaskInvocationException {
		Trigger trigger = model.getEnsembleDefinitions().get(1).getTriggers().get(0);
		
		for (ComponentInstance ci : model.getComponentInstances()) {
			Task task = new EnsembleTask(ci.getEnsembleControllers().get(1), scheduler, container, ratingsManager);
			((EnsembleTask) task).init(deecoContainer);
			task.invoke(trigger);
		}	
	}
}
