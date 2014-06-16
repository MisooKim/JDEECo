package cz.cuni.mff.d3s.deeco.simulation;

import java.util.Collection;

import cz.cuni.mff.d3s.deeco.executor.Executor;
import cz.cuni.mff.d3s.deeco.executor.SameThreadExecutor;
import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeManagerContainer;
import cz.cuni.mff.d3s.deeco.model.runtime.api.RuntimeMetadata;
import cz.cuni.mff.d3s.deeco.network.DirectGossipStrategy;
import cz.cuni.mff.d3s.deeco.network.DirectRecipientSelector;
import cz.cuni.mff.d3s.deeco.network.KnowledgeDataManager;
import cz.cuni.mff.d3s.deeco.network.PublisherTask;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeFramework;
import cz.cuni.mff.d3s.deeco.runtime.RuntimeFrameworkImpl;
import cz.cuni.mff.d3s.deeco.scheduler.Scheduler;
import cz.cuni.mff.d3s.deeco.simulation.scheduler.SimulationScheduler;

public class SimulationRuntimeBuilder {

	public RuntimeFramework build(SimulationHost host, CallbackProvider callbackProvider, RuntimeMetadata model, Collection<DirectRecipientSelector> recipientSelectors, DirectGossipStrategy directGossipStrategy) {
		if (model == null) {
			throw new IllegalArgumentException("Model must not be null");
		}

		// Set up the executor
		Executor executor = new SameThreadExecutor();

		// Set up the simulation scheduler
		Scheduler scheduler = new SimulationScheduler(host, callbackProvider);
		scheduler.setExecutor(executor);

		// Set up the host container
		KnowledgeManagerContainer container = new KnowledgeManagerContainer();

		KnowledgeDataManager kdManager = new KnowledgeDataManager(
				container, 
				host.getPacketSender(), 
				model.getEnsembleDefinitions(), 
				host.getHostId(), 
				scheduler, recipientSelectors, directGossipStrategy);
		
		// Bind KnowledgeDataReceiver with PacketDataReceiver
		host.getPacketReceiver().setKnowledgeDataReceiver(kdManager);
		
		// Set up the publisher task
		PublisherTask publisherTask = new PublisherTask(
				scheduler, 
				kdManager,				
				host.getHostId());
		
		// Add publisher task to the scheduler
		scheduler.addTask(publisherTask);

		return new RuntimeFrameworkImpl(model, scheduler, executor,
				container);
	}

	

}