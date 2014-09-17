package cz.cuni.mff.d3s.deeco.jpf;

import java.util.Arrays;
import java.util.List;

import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeManager;
import cz.cuni.mff.d3s.deeco.knowledge.RepositoryKnowledgeManager;
import cz.cuni.mff.d3s.deeco.knowledge.local.LocalKnowledgeRepositoryJPF;
import cz.cuni.mff.d3s.deeco.processor.ParsedObjectReader;
import cz.cuni.mff.d3s.deeco.provider.DEECoObjectProvider;
import cz.cuni.mff.d3s.deeco.runtime.Runtime;
import cz.cuni.mff.d3s.deeco.scheduling.Scheduler;

/**
 * 
 * @author Jaroslav Keznikl
 *
 */
public class LocalLauncherExampleJPF {

	public static void main(String[] args) {
		List<AtomicProposition> propositions = Arrays.asList(new AtomicProposition[] {
				new AtomicProposition() {					
					@Override
					public String getName() {
						return "isFollowerAtDestination";
					}
					
					@Override
					public boolean evaluate(KnowledgeJPF knowledge) {
						return knowledge.getSingle("follower.position.x").equals(knowledge.getSingle("follower.destination.x"))
								&& knowledge.getSingle("follower.position.y").equals(knowledge.getSingle("follower.destination.y"));
					}
				}
		});
		
		KnowledgeManager km = new RepositoryKnowledgeManager(new LocalKnowledgeRepositoryJPF(propositions));
		
		Scheduler scheduler = new MultithreadedSchedulerJPF();
		
		DEECoObjectProvider dop = new ParsedObjectReader().read();
		
		Runtime rt = new Runtime(km, scheduler);
		rt.registerComponentsAndEnsembles(dop);
		rt.startRuntime();
	}
}
