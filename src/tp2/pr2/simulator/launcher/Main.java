package tp2.pr2.simulator.launcher;

import org.apache.commons.cli.*;
import org.json.JSONObject;
import tp2.pr2.simulator.control.Controller;
import tp2.pr2.simulator.control.StateComparator;
import tp2.pr2.simulator.factories.*;
import tp2.pr2.simulator.model.Body;
import tp2.pr2.simulator.model.ForceLaws;
import tp2.pr2.simulator.model.PhysicsSimulator;
import tp2.pr2.simulator.view.MainWindow;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _stateComparatorDefaultValue = "epseq";
	private final static String _graficModeDefaultValue = "batch";

	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static String _inFile = null;
	private static JSONObject _forceLawsInfo = null;
	private static JSONObject _stateComparatorInfo = null;
	
	private static String _outFile = null;
	private static String _expOutFile = null;
	private static String _mode = null;
	private static Integer _Steps = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<ForceLaws> _forceLawsFactory;
	private static Factory<StateComparator> _stateComparatorFactory;

	private static void init() {
		// TODO initialize the bodies factory
		ArrayList<Builder<Body>> bodiesBuilder = new ArrayList<>();
		bodiesBuilder.add(new BasicBodyBuilder());
		bodiesBuilder.add(new MassLosingBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodiesBuilder);
		
		// TODO initialize the force laws factory
		ArrayList<Builder<ForceLaws>> fLawsBuilder = new ArrayList<>();
		fLawsBuilder.add(new NewtonUniversalGravitationBuilder());
		fLawsBuilder.add(new MovingTowardsFixedPointBuilder());
		fLawsBuilder.add(new NoForceBuilder());
		_forceLawsFactory = new BuilderBasedFactory<ForceLaws>(fLawsBuilder);

		// TODO initialize the state comparator
		ArrayList<Builder<StateComparator>> stComparator = new ArrayList<>();
		stComparator.add(new MassEqualStatesBuilder());
		stComparator.add(new EpsilonEqualStatesBuilder());
		_stateComparatorFactory = new BuilderBasedFactory<StateComparator>(stComparator);
	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseGraficModeOption(line);

			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
		
			parseOutputOption(line);
			parseSteps(line);
			parseExpectedOutputOption(line);

			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			parseStateComparatorOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		// TODO add support for -o, -eo, and -s (add corresponding information to
		// cmdLineOptions)
		
		//output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where output is written. Default value: the standard output.").build());
		
		//eo
		cmdLineOptions.addOption(Option.builder("eo").longOpt("expected-output").hasArg().desc("The expected output file. If not provided no comparison is applied.").build());
		
		//steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("An integer representing the number of simulation steps. Default value: 150.").build());

		//GUI or Batch mode
		cmdLineOptions.addOption(Option.builder("m").hasArg().desc("Execution Mode. Possible values: ’batch (Batch mode), ’gui’ (Graphical User Interface mode). Default value: " + _graficModeDefaultValue + "'.").build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());

		// gravity laws
		cmdLineOptions.addOption(Option.builder("cmp").longOpt("comparator").hasArg()
				.desc("State comparator to be used when comparing states. Possible values: "
						+ factoryPossibleValues(_stateComparatorFactory) + ". Default value: '"
						+ _stateComparatorDefaultValue + "'.")
				.build());

		return cmdLineOptions;
	}

	public static String factoryPossibleValues(Factory<?> factory) {
		if (factory == null)
			return "No values found (the factory is null)";

		String s = "";

		for (JSONObject fe : factory.getInfo()) {
			if (s.length() > 0) {
				s = s + ", ";
			}
			s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
		}

		s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
		return s;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _mode.equals("batch")) {
			throw new ParseException("In batch mode an input file of bodies is required");
		}
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {
		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		for (JSONObject fe : factory.getInfo()) {
			if (type.equals(fe.getString("type"))) {
				found = true;
				break;
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	private static void parseStateComparatorOption(CommandLine line) throws ParseException {
		String scmp = line.getOptionValue("cmp", _stateComparatorDefaultValue);
		_stateComparatorInfo = parseWRTFactory(scmp, _stateComparatorFactory);
		if (_stateComparatorInfo == null) {
			throw new ParseException("Invalid state comparator: " + scmp);
		}
	}
	
	private static void parseExpectedOutputOption(CommandLine line) throws ParseException {
		_expOutFile = line.getOptionValue("eo");
	}
	
	private static void parseOutputOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	
	private static void parseSteps(CommandLine line) throws ParseException {
		try {
			_Steps = Integer.parseInt(line.getOptionValue("s"));
		}
		catch(NumberFormatException e) {
			_Steps = null;
		}
		if(_Steps == null && _mode.equals("batch")) throw new ParseException("Input failure");
	}

	private static void parseGraficModeOption(CommandLine line) throws ParseException {
		_mode = line.getOptionValue("m", _graficModeDefaultValue);
	}

	private static void startBatchMode() throws Exception {
		ForceLaws fLaws =  _forceLawsFactory.createInstance(_forceLawsInfo);
		PhysicsSimulator sim = new PhysicsSimulator(fLaws, _dtime);
		
		InputStream is = new FileInputStream(new File(_inFile));
		OutputStream os  = _outFile == null ? System.out : new FileOutputStream(new File(_outFile));
		
		InputStream expOut = null;
		StateComparator stateCmp = null;
		
		if(_expOutFile != null) {
			expOut = new FileInputStream(new File(_expOutFile));
			stateCmp = _stateComparatorFactory.createInstance(_stateComparatorInfo);
		}
		
		Controller ctrl = new Controller(sim, _bodyFactory, _forceLawsFactory);
		ctrl.loadBodies(is);
		ctrl.run(_Steps, os, expOut, stateCmp);
		//ctrl.run(_Steps, o);
	}

	private static void startGUIMode() throws FileNotFoundException, InterruptedException, InvocationTargetException {
		ForceLaws fLaws = _forceLawsFactory.createInstance(_forceLawsInfo);
		PhysicsSimulator sim = new PhysicsSimulator(fLaws, _dtime);
		Controller ctrl = new Controller(sim, _bodyFactory, _forceLawsFactory);

		if (Main._inFile != null) {
			InputStream is = new FileInputStream(new File(Main._inFile));
			try {
				ctrl.loadBodies(is);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new MainWindow(ctrl);
			}
		});
	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if(_mode.equals("batch")) startBatchMode();
		else startGUIMode();
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("\nSomething went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
