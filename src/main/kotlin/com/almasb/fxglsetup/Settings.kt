package com.almasb.fxglsetup

import java.nio.file.Path

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
data class Settings(val outputDir: Path,
                    val packageName: String,
                    val className: String)