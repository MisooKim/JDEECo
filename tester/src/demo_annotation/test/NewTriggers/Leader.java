package demo_annotation.test.NewTriggers;



import cz.cuni.mff.d3s.deeco.annotations.*;
import cz.cuni.mff.d3s.deeco.annotations.Process;
import cz.cuni.mff.d3s.deeco.model.runtime.stateflow.InaccuracyParamHolder;
import cz.cuni.mff.d3s.deeco.model.runtime.stateflow.TSParamHolder;
import cz.cuni.mff.d3s.deeco.task.ParamHolder;


@StateSpaceModel(models = @Model(period=100,state={"lFFSpeed","lFFPos"},referenceModel=VehicleModel.class))
@Component
public class Leader {

	public String lName;

	@TimeStamp
	public Double lPos = 0.0;
	@TimeStamp
	public Double lSpeed = 0.0;
	public Double lGas = 0.0;
	public Double lBrake = 0.0;
	public Double lAlarm = 0.0;
	
	public Double lIntegratorSpeedError = 0.0;
	public Double lErrorWindup = 0.0;

	
	@TimeStamp
	public Double lFFPos = 0.0;
	@TimeStamp
	public Double lFFSpeed = 0.0;

	protected static final double KP = 0.05;
	protected static final double KI = 0.000228325;
	protected static final double KT = 0.01;
	private static final double DISEREDSPEED = 50;
	protected static final double TIMEPERIOD = 100;
	protected static final double SEC_MILI_SEC_FACTOR = 1000;
	protected static final double SEC_NANOSECOND_FACTOR = 1000000000;
	

	public Leader() {
		lName = "Leader";
	}

	@Mode(guard = "lFFPos_LH >= 100 && lFFPos_LH < 150")
  	@Process
  	@PeriodicScheduling(100)
	public static void alarmed(
			//the condition holds simple comparisons ... many simple conditions are joined with && or || operators
			//The compared value could be : V = value , TS = timestamp, L = minimum bound, H = maximum bound, LH = inaccuracy (max-min)
			//DOTO: adding the composed conditions
			@InOut("lFFPos") InaccuracyParamHolder<Double> lFFPos
 			){
  		System.out.println("alarm ....."+lFFPos.value+"  ["+lFFPos.minBoundary+" , "+lFFPos.maxBoundary+"]");
 	}

	
	@Process
	@PeriodicScheduling(value = (int) TIMEPERIOD)
	public static void speedControl(
			@InOut("lPos") TSParamHolder<Double> lPos,
			@InOut("lSpeed") TSParamHolder<Double> lSpeed,

			@InOut("lFFPos") InaccuracyParamHolder<Double> lFFPos,
			@InOut("lFFSpeed") InaccuracyParamHolder<Double> lFFSpeed,
			@InOut("lGas") ParamHolder<Double> lGas,
			@InOut("lBrake") ParamHolder<Double> lBrake,

			@InOut("lIntegratorSpeedError") ParamHolder<Double> lIntegratorSpeedError,
			@InOut("lErrorWindup") ParamHolder<Double> lErrorWindup) {
		double currentTime = System.nanoTime()/SEC_NANOSECOND_FACTOR;
		double timePeriodInSeconds = TIMEPERIOD / SEC_MILI_SEC_FACTOR;
		// uncomment instead of the original one: (it prevents any changes)
		//double speedError = 0.0;  
		double speedError = DISEREDSPEED - lSpeed.value;
		lIntegratorSpeedError.value += (KI * speedError + KT * lErrorWindup.value) * timePeriodInSeconds;
		double pid = KP * speedError + lIntegratorSpeedError.value;
		lErrorWindup.value = saturate(pid) - pid;

		if (pid >= 0) {
			lGas.value = pid;
			lBrake.value = 0.0;
		} else {
			lGas.value = 0.0;
			lBrake.value = -pid;
		}
		
		
		
		double lAcceleration = Database.getAcceleration(lSpeed.value, lPos.value, Database.fTorques, lGas.value, lBrake.value,Database.fMass);
		lSpeed.value += lAcceleration * timePeriodInSeconds; 
		lPos.value += lSpeed.value * timePeriodInSeconds;
		lPos.creationTime = currentTime;
		lSpeed.creationTime = currentTime;
		
//		System.out.println("=================================== Leader statue ==========================================");
// 		System.out.println("Speed Leader : "+lSpeed.value+", pos : "+lPos.value+"... time :"+currentTime);
// 		System.out.println("Speed Leader_FireFighter : "+lFFSpeed.value+", pos : "+lFFPos.value+"... time :"+lFFPos.creationTime);
// 		System.out.println("Inaccuracy Leader_FireFighter : pos : "+lFFPos.value+" E ["+lFFPos.minBoundary+" , "+lFFPos.maxBoundary+"] ... time :"+lFFPos.creationTime+" ... current time: "+currentTime+" ...  dt : "+(currentTime-lFFPos.creationTime));
//		System.out.println("============================================================================================");
	
	}

	
	private static double saturate(double val) {
		if (val > 1)
			val = 1;
		else if (val < -1)
			val = -1;
		return val;
	}

}