package cz.cuni.mff.d3s.deeco.executor;

/**
 * 
 * @author Jaroslav Keznikl <keznikl@d3s.mff.cuni.cz>
 *
 */
public class SingleThreadedExecutorTest extends ExecutorTest {

	@Override
	protected Executor setUpTested() {
		return new SingleThreadedExecutor();
	}

}
