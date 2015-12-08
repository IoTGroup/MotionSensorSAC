package br.ufc.great.iot.sac;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.great.ufc.adapters.cac.CACAdapter;
import br.ufc.great.loccam.ipublisher.IPublisher;
import br.ufc.great.loccam.remote.discovery.DiscoveryManager;
import br.ufc.great.loccam.remote.discovery.devices.RemoteDevice;
import br.ufc.great.loccam.remote.discovery.devices.RemoteDeviceListener;
import br.ufc.great.loccam.remote.messages.Message;
import br.ufc.great.loccam.remote.messages.MessageType;
import br.ufc.great.loccam.remote.messages.serializers.JsonSerializer;
import br.ufc.great.loccam.remote.messages.serializers.Serializer;

public class MotionSensorSAC extends CACAdapter implements RemoteDeviceListener {

	private static final String CONTEXT_KEY = "control.ambient.motion.sensor";
	private static final String SAC_NAME = "MotionSensorSAC";
	private static final String MINIMAL_VESION = "4.4.2";
	private static final String TAG = "MotionSensorSAC";
	private static final String TAG2 = "LookingDevice";

	private IPublisher iPublisher;
	private DiscoveryManager discovery;
	private RemoteDevice remoteDevice;

	private String subscribeID;
	
	private Serializer serializer = new JsonSerializer();


	public String getSubscribeID() {
		return subscribeID;
	}

	public void setSubscribeID(String subscribeID) {
		this.subscribeID = subscribeID;
	}

	@Override
	public String getClassName() {
		return MotionSensorSAC.class.getName();
	}

	@Override
	public String getContextKey() {
		return CONTEXT_KEY;
	}

	@Override
	public List<String> getDependencies() {
		return null;
	}

	@Override
	public String getMinimalVersion() {
		return MINIMAL_VESION;
	}

	@Override
	public String getName() {
		return SAC_NAME;
	}

	@Override
	public void onStart(Context arg0, IPublisher arg1) {
		iPublisher = arg1;
		discovery = DiscoveryManager.getInstance();
		remoteDevice = getRemoteDevice();
		remoteDevice.subscribe(this);
		
		while (isRunning()) {
			remoteDevice.send(createMessage());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private RemoteDevice getRemoteDevice() {
		RemoteDevice device = null;
		while (isRunning()) {
			if (discovery.hasRemoteDevices(CONTEXT_KEY)) {
				Log.d(TAG2, "Found!!!");
				device = discovery.getRemoteDevices(CONTEXT_KEY).get(0);
				break;
			}
			Log.d(TAG2, "No device found!!!");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return device;
	}


	private Message createMessage(){
		Message message = new Message();
		message.setType(MessageType.GET_REQUEST);
		message.setContextKey(CONTEXT_KEY);
		message.setAppid(SAC_NAME);
		message.setData("-1");
		message.setPrecision(-1);
		message.setTimeout(10000);
		message.setTimestamp(System.currentTimeMillis());
		message.setUuid(remoteDevice.getUuid());
		return message;
	}

	@Override
	public void onStop() {
		iPublisher.unsubscribe(getSubscribeID());
	}

	public static void main(String[] args) {
		new MotionSensorSAC().build();
	}

	@Override
	public void onMessageReceived(Message message) {
		ArrayList<Object> motionData = new ArrayList<Object>();
		motionData.add(new String(serializer.serialize(message)));
		
		Log.i(TAG,"SAS Motion Control -> mensagem recebida: " + message.getData());
		
		iPublisher.put(CONTEXT_KEY, PHYSICAL_TYPE, motionData, String.valueOf(message.getPrecision()),
				String.valueOf(message.getTimestamp()));
	}

}
