package br.ufc.teste.sac;

import android.content.Context;

import java.util.List;

import br.great.ufc.adapters.cac.CACAdapter;
import br.ufc.great.loccam.ipublisher.IPublisher;

public class HelloWorldSAC extends CACAdapter{
	
	// TODO change your Context Key here.
	private final static String CONTEXT_KEY = "<my_context_key>"; // e.g. context.ambient.light

	// TODO change your Minimal Android Version here.
	private final static String MINIMAL_VERSION = "my_minimal_version>"; // e.g. 4.0.1

	private final static String SAC_NAME = "HelloWorldSAC";
	
	private IPublisher mPublisher;
	
	@Override
	public String getClassName() {
		return HelloWorldSAC.class.getName();
	}

	@Override
	public String getContextKey() {
		return CONTEXT_KEY;
	}

	@Override
	public List<String> getDependencies() {
		// TODO List of dependencies, null if the SAC do not depend of other SACs.
		return null;
	}

	@Override
	public String getMinimalVersion() {
		return MINIMAL_VERSION;
	}

	@Override
	public String getName() {
		return SAC_NAME;
	}

	@Override
	public void onStart(Context context, IPublisher publisher) {
		mPublisher = publisher;

		// TODO Start code here.
	}

	@Override
	public void onStop() {
		// TODO Stop code here.
	}

	public static void main(String[] args) {
		new HelloWorldSAC().build(); // build the jar file for this SAC.
	}
}

