package cz.cuni.mff.d3s.jdeeco.matsim.simulation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.core.mobsim.framework.Mobsim;

public class DefaultMATSimExtractor implements MATSimExtractor {

	@Override
	public Map<Id, MATSimOutput> extractFromMATSim(Collection<JDEECoAgent> agents,
			Mobsim mobsim) {
		Map<Id, MATSimOutput> map = new HashMap<Id, MATSimOutput>();
		MATSimOutput matSimOutput;
		for (JDEECoAgent agent : agents) {
			matSimOutput = new MATSimOutput(agent.getCurrentLinkId(),
					agent.getState());
			map.put(agent.getId(), matSimOutput);
		}
		return map;
	}

}
