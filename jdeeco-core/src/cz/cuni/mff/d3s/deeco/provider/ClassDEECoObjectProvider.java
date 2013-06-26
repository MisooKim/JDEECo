package cz.cuni.mff.d3s.deeco.provider;

import static cz.cuni.mff.d3s.deeco.processor.ComponentParser.extractComponentProcess;
import static cz.cuni.mff.d3s.deeco.processor.ComponentParser.extractInitialKnowledge;
import static cz.cuni.mff.d3s.deeco.processor.ComponentParser.extractComponentFields;
import static cz.cuni.mff.d3s.deeco.processor.ComponentParser.isComponentDefinition;
import static cz.cuni.mff.d3s.deeco.processor.EnsembleParser.extractEnsembleProcess;
import static cz.cuni.mff.d3s.deeco.processor.EnsembleParser.isEnsembleDefinition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cz.cuni.mff.d3s.deeco.exceptions.KMAccessException;
import cz.cuni.mff.d3s.deeco.invokable.SchedulableComponentProcess;
import cz.cuni.mff.d3s.deeco.invokable.SchedulableEnsembleProcess;
import cz.cuni.mff.d3s.deeco.knowledge.Component;
import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeManager;
import cz.cuni.mff.d3s.deeco.logging.Log;
import cz.cuni.mff.d3s.deeco.path.grammar.ParseException;
import cz.cuni.mff.d3s.deeco.performance.ComponentKnowledgeDecomposer;
import cz.cuni.mff.d3s.deeco.performance.KnowledgeDecomposer;
import cz.cuni.mff.d3s.deeco.performance.KnowledgeInfo;
import cz.cuni.mff.d3s.deeco.processor.ComponentParser;
import cz.cuni.mff.d3s.deeco.runtime.Runtime;

/**
 * Provider class dealing with component and ensemble definitions retrieved from
 * Class definitions.
 * 
 * @author Michal Kit
 * 
 */
public class ClassDEECoObjectProvider extends AbstractDEECoObjectProvider {

	private static final long serialVersionUID = -1454153558315293318L;

	protected final List<Class<?>> rawComponents;
	protected final List<Class<?>> rawEnsembles;

	public ClassDEECoObjectProvider() {
		this(new LinkedList<Class<?>>(), new LinkedList<Class<?>>());
	}

	public ClassDEECoObjectProvider(List<Class<?>> components,
			List<Class<?>> ensembles) { 
		rawComponents = components;
		rawEnsembles = ensembles;
	}

	public ClassDEECoObjectProvider(List<Class<?>> components,
			List<Class<?>> ensembles, KnowledgeManager km) {
		this(components, ensembles);
		this.km=km;
	}
	

	/**
	 * Adds either compontent or ensemble definition class to its internal
	 * collection.
	 * 
	 * @param clazz
	 *            Class to be added.
	 * @return Addition result.
	 */
	public boolean addDEECoObjectClass(Class<?> clazz) {
		if (isComponentDefinition(clazz))
			rawComponents.add(clazz);
		else if (isEnsembleDefinition(clazz))
			rawEnsembles.add(clazz);
		else
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.provider.AbstractDEECoObjectProvider#processComponents
	 * ()
	 */
	@Override
	protected synchronized void processComponents() {
		components = new LinkedList<ParsedComponent>();
		for (Class<?> c : rawComponents) {
			Component component = extractInitialKnowledge(c);
			components.add(new ParsedComponent(extractComponentProcess(c,component.id), component));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.provider.AbstractDEECoObjectProvider#processEnsembles
	 * ()
	 */
	@Override
	protected synchronized void processEnsembles() {
		ensembles = new LinkedList<SchedulableEnsembleProcess>();
		for (Class<?> c : rawEnsembles) {
			try {
				ensembles.add(extractEnsembleProcess(c));
			} catch (ParseException e) {
				Log.e(String.format("Parsing error in class '%s': %s",
						c.getName(), e.getMessage()), e);
			}
		}
	}

	@Override
	protected void processKnowledges() {
		// TODO Auto-generated method stub
		knowledgePaths=new ArrayList<String>();
		int i=0;
		for (Class<?> c : rawComponents) {
			knowledgePaths.addAll(extractComponentFields(c,components.get(i++).getInitialKnowledge().id ,km));
		}
	}

}
