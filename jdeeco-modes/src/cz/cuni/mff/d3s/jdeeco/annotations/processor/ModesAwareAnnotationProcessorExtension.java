/*******************************************************************************
 * Copyright 2015 Charles University in Prague
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
package cz.cuni.mff.d3s.jdeeco.annotations.processor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.cuni.mff.d3s.deeco.annotations.processor.AnnotationProcessor;
import cz.cuni.mff.d3s.deeco.annotations.processor.AnnotationProcessorExtensionPoint;
import cz.cuni.mff.d3s.deeco.logging.Log;
import cz.cuni.mff.d3s.deeco.model.runtime.api.ComponentInstance;
import cz.cuni.mff.d3s.deeco.model.runtime.api.ComponentProcess;
import cz.cuni.mff.d3s.deeco.modes.DEECoMode;
import cz.cuni.mff.d3s.jdeeco.annotations.ComponentModeChart;
import cz.cuni.mff.d3s.jdeeco.annotations.ExcludeMode;
import cz.cuni.mff.d3s.jdeeco.annotations.ExcludeModes;
import cz.cuni.mff.d3s.jdeeco.annotations.Mode;
import cz.cuni.mff.d3s.jdeeco.annotations.Modes;
import cz.cuni.mff.d3s.jdeeco.modes.ModeChartHolder;
import cz.cuni.mff.d3s.jdeeco.modes.ModeChartImpl;

/**
 * Processes the annotations related to DEECo processes' modes.
 *
 * @author Ilias Gerostathopoulos <iliasg@d3s.mff.cuni.cz>
 * @see AnnotationProcessor
 */
public class ModesAwareAnnotationProcessorExtension extends AnnotationProcessorExtensionPoint {

	/**
	 * Keeps the mode classes included in the mode chart associated with the
	 * component currently being parsed
	 */
	private Set<DEECoMode> includedModes = new HashSet<>();
	
	@Override
	public void onComponentInstanceCreation(ComponentInstance componentInstance, Annotation unknownAnnotation) {
		if (unknownAnnotation instanceof ComponentModeChart) {
			Class<? extends ModeChartHolder> modeChartHolder = ((ComponentModeChart) unknownAnnotation).value();
			try {
				ModeChartHolder holderInstance = modeChartHolder.newInstance();
				holderInstance.validate();
				
				ModeChartImpl modeChart = new ModeChartImpl(componentInstance);
				modeChart.setModes(holderInstance.getModes());
				modeChart.setTransitions(holderInstance.getTransitions());
				modeChart.setTransitionListeners(holderInstance.getTransitionListeners());
				modeChart.setInitialMode(holderInstance.getInitialMode());
				
				includedModes = modeChart.getModes();
				
				componentInstance.setModeChart(modeChart);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onComponentProcessCreation(ComponentProcess componentProcess, Annotation unknownAnnotation) {
		if (unknownAnnotation instanceof Mode) {
			Class<? extends DEECoMode> modeClass = ((Mode) unknownAnnotation).value();
			try {
				componentProcess.getModes().add((modeClass.newInstance()));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (unknownAnnotation instanceof Modes) {
			Class<? extends DEECoMode>[] modeClasses = ((Modes) unknownAnnotation).value();
			
			for (Class<? extends DEECoMode> modeClass : modeClasses) {
				try {
					componentProcess.getModes().add((modeClass.newInstance()));
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}	
			}
		}
		if (unknownAnnotation instanceof ExcludeMode) {
			Class<? extends DEECoMode> excludeModeClass = ((ExcludeMode) unknownAnnotation).value();
			
			if (includedModes.isEmpty()) {
				Log.w("When using the ExcludeMode annotation you should include some modes in the mode chart of the component.");
				return;
			}
			
			for (DEECoMode mode : includedModes) {
				if (!excludeModeClass.equals(mode.getClass())) {
					componentProcess.getModes().add((mode));
				}
			}
		}
		if (unknownAnnotation instanceof ExcludeModes) {
			List<Class<? extends DEECoMode>> excludeModeClasses = Arrays.asList(((ExcludeModes) unknownAnnotation).value());
			
			if (includedModes.isEmpty()) {
				Log.w("When using the ExcludeModes annotation you should include some modes in the mode chart of the component.");
				return;
			}
			
			for (DEECoMode mode : includedModes) {
				if (!excludeModeClasses.contains(mode.getClass())) {
					componentProcess.getModes().add((mode));
				}
			}
		}
	}

}
