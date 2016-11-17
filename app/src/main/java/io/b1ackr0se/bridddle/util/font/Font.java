package io.b1ackr0se.bridddle.util.font;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static io.b1ackr0se.bridddle.util.font.Font.FIRASANS;
import static io.b1ackr0se.bridddle.util.font.Font.FIRASANS_BOLD;
import static io.b1ackr0se.bridddle.util.font.Font.FIRASANS_BOLD_ITALIC;
import static io.b1ackr0se.bridddle.util.font.Font.FIRASANS_ITALIC;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        FIRASANS,
        FIRASANS_BOLD,
        FIRASANS_BOLD_ITALIC,
        FIRASANS_ITALIC
})

public @interface Font {
    String FIRASANS = "FiraSans-Regular.ttf";
    String FIRASANS_BOLD = "FiraSans-Bold.ttf";
    String FIRASANS_ITALIC = "FiraSans-Italic.ttf";
    String FIRASANS_BOLD_ITALIC = "FiraSans-BoldItalic.ttf";
}
