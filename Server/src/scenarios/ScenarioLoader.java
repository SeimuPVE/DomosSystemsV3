package scenarios;


public class ScenarioLoader {
    private ScenarioSunLight scenarioSunLight;

    public ScenarioLoader() {
        scenarioSunLight = new ScenarioSunLight();
    }

    public void exec(String cmd) {
        scenarioSunLight.exec(cmd);
    }
}
