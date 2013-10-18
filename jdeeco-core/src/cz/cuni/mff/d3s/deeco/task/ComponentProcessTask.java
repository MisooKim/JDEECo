package cz.cuni.mff.d3s.deeco.task;

import java.util.List;

import cz.cuni.mff.d3s.deeco.model.ComponentProcess;
import cz.cuni.mff.d3s.deeco.model.LockingMode;
import cz.cuni.mff.d3s.deeco.model.Parameter;
import cz.cuni.mff.d3s.deeco.model.Schedule;

public class ComponentProcessTask extends Task {

	private final ComponentProcess componentProcess;
	private final String componentId;

	public ComponentProcessTask(ComponentProcess componentProcess,
			String componentId) {
		this.componentId = componentId;
		this.componentProcess = componentProcess;
	}

	@Override
	public Schedule getSchedule() {
		return componentProcess.getSchedule();
	}

	public List<Parameter> getParameters() {
		return componentProcess.getParameters();
	}

	public String getComponentId() {
		return componentId;
	}

	public void execute(Object[] parameterValues) throws Exception {
		super.execute(componentProcess, parameterValues);
	}
	
	public LockingMode getLockingMode() {
		return componentProcess.getLockingMode();
	}

	public ComponentProcess getComponentProcess() {
		return componentProcess;
	}
}