package haven;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.HashSet;
import java.net.URL;

public class Sound{
	private AudioClip m_clip;
	public static HashSet<Integer> soundSet = new HashSet<Integer>();
	public static HashSet<Integer> deathSet = new HashSet<Integer>();
	
	public static final Sound white = new Sound("custom_wav/whiteFound.wav");
	public static final Sound red = new Sound("custom_wav/redFound.wav");
	public static final Sound troll = new Sound("custom_wav/trollFound.wav");
	public static final Sound bell = new Sound("custom_wav/bellFound.wav");
	public static final Sound flotsam = new Sound("custom_wav/flotsamFound.wav");
	public static final Sound bear = new Sound("custom_wav/bearFound.wav");
	public static final Sound pearl = new Sound("custom_wav/pearlFound.wav");
	public static final Sound aggro = new Sound("custom_wav/aggroSound.wav");
	public static final Sound death = new Sound("custom_wav/deathSound.wav");
	public static final Sound ram = new Sound("custom_wav/ramFound.wav");
	
	public static void soundGobList(Gob g){
		if(!soundCheck(g.id)) return;
		
		String resname = g.resname();
		
		if(resname == ""){
			soundSet.remove(g.id);
		}
		
		if(g.isHuman() && !isplayerid(g.id) ){
			KinInfo kin = g.getattr(KinInfo.class);
			if(kin == null){
				safePlay("white");
			}else if(kin.group == 2){
				safePlay("red");
			}
		}else if(resname.endsWith("troll/s") ){
			safePlay("troll");
		}else if(resname.endsWith("bear/s") ){
			safePlay("bear");
		}else if(resname.startsWith("gfx/kritter/bram") ){
			safePlay("ram");
		}else if(resname.endsWith("chimingbluebell") ){
			safePlay("bell");
		}else if(resname.endsWith("flotsam") ){
			safePlay("flotsam");
		}
	}
	
	public static boolean isplayerid(int id){
		if((UI.instance != null)
			&& (UI.instance.mainview != null)
			&& (UI.instance.mainview.playergob == id)){
			return true;
		}
		return false;
    }
	
	public static boolean soundCheck(int id){
		if(!soundSet.contains(id) ){
			soundSet.add(id);
			return true;
		}
		return false;
	}
	
	public static boolean deathCheck(int id){
		if(!deathSet.contains(id) ){
			deathSet.add(id);
			return true;
		}
		return false;
	}
	
	public static void safePlay(String str){
		if(!Config.confSounds.get(str)) return;
		
		playSound(str);
	}
	
	public static void playSound(String str){
		if(str == "white") Sound.white.play();
		if(str == "red") Sound.red.play();
		if(str == "troll") Sound.troll.play();
		if(str == "bell") Sound.bell.play();
		if(str == "flotsam") Sound.flotsam.play();
		if(str == "bear") Sound.bear.play();
		if(str == "pearl") Sound.pearl.play();
		if(str == "aggro") Sound.aggro.play();
		if(str == "death") Sound.death.play();
		if(str == "ram") Sound.ram.play();
	}
	
	public Sound(String fileName){
		try{
			URL url = new URL("file:"+fileName);
			m_clip = Applet.newAudioClip(url);
			//System.out.println(fileName);
		}catch(Exception e){
			System.out.println("Clip loading error.");
		}
	}
	
	public void play(){
		try{
			new Thread(){
				public void run(){
					m_clip.play();
				}
			}.start();
		}catch(Exception ec){
			System.out.println("Play error.");
		}
	}
}