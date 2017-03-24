/*******************************************************************************
 * Copyright 2017 Charles University in Prague
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *******************************************************************************/
package cz.cuni.mff.d3s.jdeeco.adaptation.modeswitchprops;

import cz.cuni.mff.d3s.deeco.model.runtime.api.ComponentInstance;
import cz.cuni.mff.d3s.jdeeco.adaptation.AdaptationUtility;

/**
 * @author Dominik Skoda <skoda@d3s.mff.cuni.cz>
 *
 */
public class ComponentImpl implements cz.cuni.mff.d3s.metaadaptation.modeswitchprops.Component {

	private final ComponentInstance component;
	
	private final AdaptationUtility utility;
	
	public ComponentImpl(ComponentInstance component, AdaptationUtility utility){
		if(component == null){
			throw new IllegalArgumentException(String.format("The %s argument is null.", "component"));
		}
		if(utility == null){
			throw new IllegalArgumentException(String.format("The %s argument is null.", "utility"));
		}
		
		this.component = component;
		this.utility = utility;
	}
	
	/* (non-Javadoc)
	 * @see cz.cuni.mff.d3s.metaadaptation.modeswitchprops.Component#getUtility()
	 */
	@Override
	public double getUtility() {
		return utility.getUtility(component);
	}

	/* (non-Javadoc)
	 * @see cz.cuni.mff.d3s.metaadaptation.modeswitchprops.Component#getUtilityThreshold()
	 */
	@Override
	public double getUtilityThreshold() {
		return utility.getUtilityThreshold();
	}

}
