package cz.cuni.mff.d3s.deeco.knowledge.local;

import cz.cuni.mff.d3s.deeco.knowledge.KnowledgeChangeCollector;

class LocalSession extends KnowledgeChangeCollector {

	private boolean succeeded = false;
	private final LocalKnowledgeRepository kr;
	
	LocalSession(LocalKnowledgeRepository kr) {
		this.kr = kr;
	}
	
	@Override
	public void begin() {
		kr.lock.lock();

		// we must break transition here because there may be no thread choices at "park/unpark"
		// not necessary when we limit consecutive runs of every process
		//Thread.yield();
	}

	@Override
	public void end() {
		kr.lock.unlock();
		notifyAboutKnowledgeChange();
		succeeded = true;
	}
	

	@Override
	public void cancel() {
		kr.lock.unlock();
	}

	@Override
	public boolean repeat() {
		return !succeeded;
	}

	@Override
	public boolean hasSucceeded() {
		return succeeded;
	}

}
