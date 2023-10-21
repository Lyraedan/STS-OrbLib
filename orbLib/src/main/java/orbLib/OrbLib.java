package orbLib;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.PostInitializeSubscriber;
import orbLib.util.IDCheckDontTouchPls;
import orbLib.util.OrbListener;
import orbLib.util.TextureLoader;

@SpireInitializer
public class OrbLib implements PostInitializeSubscriber {

	public static final Logger logger = LogManager.getLogger(OrbLib.class.getName());
	private static String modID;

	public static Properties theOrbLibSettings = new Properties();
	public static final String EVOKE_ALL_ORBS_ON_FULL = "evokeAllOrbsOnFull";
	public static final String PATCH_DEFECT = "patchDefect";
	public static final String DEFECT_EVOKE_ALL_ORBS_ON_FULL = "defectEvokeAllOrbsOnFull";
	public static boolean CONFIG_EVOKE_ALL_ORBS_ON_FULL = true;
	public static boolean CONFIG_PATCH_DEFECT = true;
	public static boolean CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL = true;

	private static final String MODNAME = "OrbLib";
	private static final String AUTHOR = "ItsLuke";
	private static final String DESCRIPTION = "A library for extending orb functionality";

	public static OrbListener orbListener = new OrbListener();

	public static final String BADGE_IMAGE = "orbLibResources/images/Badge.png";

	public static String makeCardPath(String resourcePath) {
		return getModID() + "Resources/images/cards/" + resourcePath;
	}

	public static String makeRelicPath(String resourcePath) {
		return getModID() + "Resources/images/relics/" + resourcePath;
	}

	public static String makeRelicOutlinePath(String resourcePath) {
		return getModID() + "Resources/images/relics/outline/" + resourcePath;
	}

	public static String makeOrbPath(String resourcePath) {
		return getModID() + "Resources/images/orbs/" + resourcePath;
	}

	public static String makePowerPath(String resourcePath) {
		return getModID() + "Resources/images/powers/" + resourcePath;
	}

	public static String makeEventPath(String resourcePath) {
		return getModID() + "Resources/images/events/" + resourcePath;
	}
	
	public static String makeOrbIntentPath(String resourcePath) {
		return getModID() + "Resources/images/orbs/intents/" + resourcePath;
	}

	public OrbLib() {
		logger.info("Subscribe to BaseMod hooks");

		BaseMod.subscribe(this);

		setModID("orbLib");
		logger.info("Done subscribing");

		logger.info("Adding mod settings");

		theOrbLibSettings.setProperty(EVOKE_ALL_ORBS_ON_FULL, "TRUE");
		theOrbLibSettings.setProperty(PATCH_DEFECT, "FALSE");
		theOrbLibSettings.setProperty(DEFECT_EVOKE_ALL_ORBS_ON_FULL, "FALSE");
		try {
			SpireConfig config = new SpireConfig("OrbLib", "OrbLibConfig", theOrbLibSettings);
			config.load();
			CONFIG_EVOKE_ALL_ORBS_ON_FULL = config.getBool(EVOKE_ALL_ORBS_ON_FULL);
			CONFIG_PATCH_DEFECT = config.getBool(PATCH_DEFECT);
			CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL = config.getBool(DEFECT_EVOKE_ALL_ORBS_ON_FULL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Done adding mod settings");
	}

	public static void setModID(String ID) {
		Gson coolG = new Gson();
		// String IDjson =
		// Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
		InputStream in = OrbLib.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
		IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
				IDCheckDontTouchPls.class);
		logger.info("You are attempting to set your mod ID as: " + ID);
		if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
			throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
		} else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
			modID = EXCEPTION_STRINGS.DEFAULTID;
		} else {
			modID = ID;
		}
		logger.info("Success! ID is " + modID);
	}

	public static String getModID() {
		return modID;
	}

	private static void pathCheck() {
		Gson coolG = new Gson();
		// String IDjson =
		// Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
		InputStream in = OrbLib.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
		IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8),
				IDCheckDontTouchPls.class);
		String packageName = OrbLib.class.getPackage().getName();
		FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
		if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
			if (!packageName.equals(getModID())) {
				throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
			}
			if (!resourcePathExists.exists()) {
				throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
			}
		}
	}

	public static void initialize() {
		logger.info("========================= Initializing OrbLib. =========================");
		OrbLib orbLib = new OrbLib();
		logger.info("========================= /OrbLib Initialized./ =========================");
	}

	@Override
	public void receivePostInitialize() {
		logger.info("Loading badge image and mod options");

		Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

		ModPanel settingsPanel = new ModPanel();

		ModLabeledToggleButton toggleEvokeAll = new ModLabeledToggleButton(
				"Evoke all orbs when full? (Doesn't affect The Defect)", 350.0f, 700.0f, Settings.CREAM_COLOR,
				FontHelper.charDescFont, CONFIG_EVOKE_ALL_ORBS_ON_FULL, settingsPanel, (label) -> {
				}, (button) -> {

					CONFIG_EVOKE_ALL_ORBS_ON_FULL = button.enabled;
					try {
						SpireConfig config = new SpireConfig("OrbLib", "OrbLibConfig", theOrbLibSettings);
						config.setBool(EVOKE_ALL_ORBS_ON_FULL, CONFIG_EVOKE_ALL_ORBS_ON_FULL);
						config.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
		
		ModLabeledToggleButton patchDefect = new ModLabeledToggleButton(
				"Patch Defect", 350.0f, 730.0f, Settings.CREAM_COLOR,
				FontHelper.charDescFont, CONFIG_PATCH_DEFECT, settingsPanel, (label) -> {
				}, (button) -> {

					CONFIG_PATCH_DEFECT = button.enabled;
					try {
						SpireConfig config = new SpireConfig("OrbLib", "OrbLibConfig", theOrbLibSettings);
						config.setBool(PATCH_DEFECT, CONFIG_PATCH_DEFECT);
						config.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
		
		ModLabeledToggleButton defectEvokeAll = new ModLabeledToggleButton(
				"Defect - Evoke all orbs when full. (Requires Patch Defect)", 350.0f, 760.0f, Settings.CREAM_COLOR,
				FontHelper.charDescFont, CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL, settingsPanel, (label) -> {
				}, (button) -> {

					CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL = button.enabled;
					try {
						SpireConfig config = new SpireConfig("OrbLib", "OrbLibConfig", theOrbLibSettings);
						config.setBool(DEFECT_EVOKE_ALL_ORBS_ON_FULL, CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL);
						config.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		settingsPanel.addUIElement(toggleEvokeAll);
		settingsPanel.addUIElement(patchDefect);
		settingsPanel.addUIElement(defectEvokeAll);

		BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

		logger.info("Done loading badge Image and mod options");
	}

	public static String makeID(String idText) {
		return getModID() + ":" + idText;
	}
}
