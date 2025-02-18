package desenvolvimento;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;
	
	public static final Sound musicBackGround = new Sound("/backGroundMus.wav");
	public static final Sound hitFx = new Sound("/Hit_Hurt.wav");
	public static final Sound colectFx = new Sound("/Colect_lixo.wav");
	public static final Sound selectFx = new Sound("/Select.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {}
	}
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		} catch (Throwable e) {}
	}
}
