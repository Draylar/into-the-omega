package draylar.intotheomega.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

public class ITOConfig implements Config {

    @Comment("If true, ITO will override the title screen splash messages with thematic replacements.")
    public boolean splashOverride = true;

    @Override
    public String getName() {
        return "into-the-omega";
    }

    @Override
    public String getExtension() {
        return "json5";
    }
}
