/*******************************************************************************
 * Copyright 2012 Charles University in Prague
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package cz.cuni.mff.d3s.deeco.runtime;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cz.cuni.mff.d3s.deeco.exceptions.KMException;
import cz.cuni.mff.d3s.deeco.exceptions.KMNotExistentException;
import cz.cuni.mff.d3s.deeco.invokable.SchedulableComponentProcess;
import cz.cuni.mff.d3s.deeco.invokable.SchedulableEnsembleProcess;
import cz.cuni.mff.d3s.deeco.invokable.SchedulableProcess;
import cz.cuni.mff.d3s.deeco.knowledge.Component;
import cz.cuni.mff.d3s.deeco.knowledge.ConstantKeys;
import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeManager;
import cz.cuni.mff.d3s.deeco.logging.Log;
import cz.cuni.mff.d3s.deeco.performance.ComponentKnowledgeDecomposer;
import cz.cuni.mff.d3s.deeco.performance.KnowledgeInfo;
import cz.cuni.mff.d3s.deeco.provider.AbstractDEECoObjectProvider;
import cz.cuni.mff.d3s.deeco.provider.ParsedComponent;
import cz.cuni.mff.d3s.deeco.scheduling.IScheduler;
import cz.cuni.mff.d3s.deeco.scheduling.SchedulerUtils;

/**
 * Class representing jDEECo runtime
 * 
 * @author Michal Kit
 * 
 */

public class Runtime implements IRuntime {

	private IScheduler scheduler;
	public KnowledgeManager km;


	private static List<Runtime> runtimes = new LinkedList<Runtime>();

	public Runtime() {
		runtimes.add(this);
	}

	public Runtime(KnowledgeManager km) {
		this();
		this.km = km;
	}

	public Runtime(IScheduler scheduler) {
		this();
		this.scheduler = scheduler;
	}

	public Runtime(KnowledgeManager km, IScheduler scheduler) {
		this();
		this.km = km;
		this.scheduler = scheduler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.runtime.IRuntime#setScheduler(java.lang.Object)
	 */
	@Override
	public void setScheduler(Object scheduler) {
		unsetScheduler(scheduler);
		if (scheduler instanceof IScheduler)
			this.scheduler = (IScheduler) scheduler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.runtime.IRuntime#unsetScheduler(java.lang.Object)
	 */
	@Override
	public void unsetScheduler(Object scheduler) {
		if (this.scheduler != null)
			this.scheduler.clearAll();
		this.scheduler = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.runtime.IRuntime#setKnowledgeManager(java.lang.
	 * Object)
	 */
	@Override
	public void setKnowledgeManager(Object km) {
		unsetKnowledgeManager(null);
		this.km = (KnowledgeManager) km;
	}

	@Override
	public void unsetKnowledgeManager(Object km) {
		stopRuntime();
		this.km = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.runtime.IRuntime#registerComponentsAndEnsembles
	 * (cz.cuni.mff.d3s.deeco.provider.AbstractDEECoObjectProvider)
	 */
	@Override
	public void registerComponentsAndEnsembles(
			AbstractDEECoObjectProvider provider) {
		ClassLoader contextClassLoader = provider.getContextClassLoader();

		List<? extends SchedulableProcess> ensembleProcesses = provider
				.getEnsembles();
		setUpProcesses(ensembleProcesses, km, contextClassLoader);
		addSchedulableProcesses(ensembleProcesses);

		for (ParsedComponent component : provider.getComponents()) {
			try {
				initComponentKnowledge(component.getInitialKnowledge(), km);
			} catch (Exception e) {
				Log.e(String.format(
						"Error when initializing knowledge of component %s",
						component.getInitialKnowledge().getClass()), e);
				continue;
			}
			List<? extends SchedulableProcess> componentProcesses = component
					.getProcesses();
			setUpProcesses(componentProcesses, km, contextClassLoader);
			addSchedulableProcesses(componentProcesses);
		}
		registerAllKnowlege(provider);
		
	}

	///////////////////////////////////////////
	///////////////////////////////////////////
	
	public void registerAllKnowlege(AbstractDEECoObjectProvider provider){
	    List<KnowledgeInfo> kList=null;
	    List<String> ids = getComponentsIds();
	    ComponentKnowledgeDecomposer kd2=null;
	    List<String> result=null;
		for (String id: ids) {
				Object ck = getComponentKnowledge(id);
				kd2=new ComponentKnowledgeDecomposer(id,ck, km);
				kd2.decompose();
				result = provider.getKnowledges();
				kd2.filter(result);	
	    }
	}

	///////////////////////////////////////////
	//////////////////////////////////////////
	
	/**
	 * Set-up the processes for runtime execution; i.e., assign them knowledge
	 * manager reference and classloader reference.
	 * 
	 * @param processes
	 *            processes to be set up
	 * @param km
	 *            knowledge manager
	 * @param contextClassLoader
	 *            classloader for the process
	 */
	private void setUpProcesses(List<? extends SchedulableProcess> processes,
			KnowledgeManager km, ClassLoader contextClassLoader) {
		for (SchedulableProcess p : processes) {
			p.km = km;
			p.contextClassLoader = contextClassLoader;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.runtime.IEnsembleComponentInformer#getComponentsIds
	 * ()
	 */
	@Override
	public List<String> getComponentsIds() {
		List<String> result = new LinkedList<String>();
		try {
			Object[] ids = (Object[]) km
					.getKnowledge(ConstantKeys.ROOT_KNOWLEDGE_ID);
			for (Object id : ids)
				result.add((String) id);
		} catch (KMException e) {
			System.err.println("GOTCHA 1");
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.runtime.IRuntime#getComponentKnowledge(java.lang
	 * .String)
	 */
	@Override
	public Object getComponentKnowledge(String componentId) {
		try {
			return km.getKnowledge(componentId);
		} catch (Exception e) {
			System.err.println("GOTCHA 2");
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.cuni.mff.d3s.deeco.runtime.IRuntime#getComponentProcesses(java.lang
	 * .String)
	 */
	@Override
	public List<SchedulableComponentProcess> getComponentProcesses(
			String componentId) {
		if (scheduler != null)
			return SchedulerUtils.getComponentProcesses(componentId, scheduler);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.cuni.mff.d3s.deeco.runtime.IRuntime#getEnsembleProcesses()
	 */
	@Override
	public List<SchedulableEnsembleProcess> getEnsembleProcesses() {
		if (scheduler != null)
			return SchedulerUtils.getEnsembleProcesses(scheduler);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.cuni.mff.d3s.deeco.runtime.IRuntime#isRunning()
	 */
	@Override
	public synchronized boolean isRunning() {
		return scheduler.isRunning();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.cuni.mff.d3s.deeco.runtime.IRuntime#startRuntime()
	 */
	@Override
	public synchronized void startRuntime() {
		scheduler.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.cuni.mff.d3s.deeco.runtime.IRuntime#stopRuntime()
	 */
	@Override
	public synchronized void stopRuntime() {
		scheduler.clearAll();
	}

	/**
	 * Registers single schedulable process within the scheduler.
	 * 
	 * @param process
	 *            process that needs to be registered
	 */
	private synchronized void addSchedulableProcess(SchedulableProcess process) {
		if (process != null)
			scheduler.add(process);
	}

	/**
	 * Registers a collection of schedulable processes within the scheduler.
	 * 
	 * @param processes
	 */
	private synchronized void addSchedulableProcesses(
			List<? extends SchedulableProcess> processes) {
		if (processes != null)
			for (SchedulableProcess sp : processes) {
				addSchedulableProcess(sp);
			}
	}

	/**
	 * Adds component knowledge into the knowledge manager.
	 * 
	 * @param initKnowledge
	 *            component knowledge that needs to be added to the knowledge
	 *            manager.
	 * @param km
	 *            reference to the knowledge manager.
	 * @throws Exception
	 *             in case the knowledge couldn't be initialized, the message
	 *             contains the reason.
	 */
	private synchronized void initComponentKnowledge(Component initKnowledge,
			KnowledgeManager km) throws Exception {
		if ((initKnowledge == null) || (km == null))
			throw new NullPointerException();

		try {
			Object[] currentIds = (Object[]) km
					.getKnowledge(ConstantKeys.ROOT_KNOWLEDGE_ID);
			if (Arrays.asList(currentIds).contains(initKnowledge.id))
				throw new Exception(String.format(
						"Knowledge of a component with id '%s' already exists",
						initKnowledge.id));
		} catch (KMNotExistentException kmnee) {
		}

		km.putKnowledge(ConstantKeys.ROOT_KNOWLEDGE_ID, initKnowledge.id);
		km.alterKnowledge(initKnowledge.id, initKnowledge);
	}

	/**
	 * Returns the Runtime singleton object. Works if only one Runtime object
	 * has been created. Otherwise it is not supported.
	 * 
	 * @return The Runtime singleton object.
	 * @throws UnsupportedOperationException
	 *             Thrown when no Runtime or more Runtimes are instantiated.
	 */
	public static IRuntime getDefaultRuntime()
			throws UnsupportedOperationException {
		if (runtimes.size() != 1) {
			throw new UnsupportedOperationException();
		} else {
			return runtimes.get(0);
		}
	}
}
