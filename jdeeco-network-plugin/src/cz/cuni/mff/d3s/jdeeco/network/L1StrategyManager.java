package cz.cuni.mff.d3s.jdeeco.network;

import java.util.Collection;

import cz.cuni.mff.d3s.jdeeco.network.l1.L1Strategy;

/**
 * Interface for Layer 1 network
 * 
 * @author Vladimir Matena <matena@d3s.mff.cuni.cz>
 *
 */
public interface L1StrategyManager {
	/**
	 * Registers L1 strategy for processing L1 packets
	 * 
	 * @param strategy
	 *            Strategy to register
	 */
	public void registerL1Strategy(L1Strategy strategy);

	/**
	 * Gets registered L1 strategies
	 * 
	 * @return Read-only collection of registered strategies
	 */
	public Collection<L1Strategy> getRegisteredL1Strategies();

	/**
	 * Removes L1 strategy
	 * 
	 * @param strategy
	 *            Strategy to remove
	 */
	public boolean unregisterL1Strategy(L1Strategy strategy);
}
