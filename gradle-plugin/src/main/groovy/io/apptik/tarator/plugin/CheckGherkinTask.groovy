package io.apptik.tarator.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.VerificationTask

class CheckGherkinTask extends DefaultTask implements VerificationTask {


    @Override
    void setIgnoreFailures(boolean b) {

    }

    @Override
    boolean getIgnoreFailures() {
        return false
    }
}