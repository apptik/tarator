package io.apptik.tarator.plugin

import com.android.build.gradle.AppPlugin;
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin;
import org.gradle.api.Project;

class TaratorAndroidPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {

        if (!project.plugins.withType(AppPlugin) && !project.plugins.withType(LibraryPlugin)) {
            throw new IllegalStateException("Android plugin not found")
        }

        project.task("checkGherkin", type: CheckGherkinTask) {

        }

        project.task("deviceCucTest", type: DeviceCucTestTask) {

        }
    }
}