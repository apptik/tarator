package io.apptik.tarator.plugin

import org.gradle.api.Project
import org.gradle.api.internal.plugins.PluginApplicationException
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class TaratorAndroidPluginTest extends Specification {

    Project p;
    def prepProject() {
       p = ProjectBuilder.builder().build()
    }

    def prepPlugin() {
        p.pluginManager.apply 'io.apptik.tarator-android'
    }

    def "should require Android plugin"() {
        given:
        prepProject()
        when:
        prepPlugin()
        then:
        def e = thrown(PluginApplicationException.class)
        e.cause.message == "Android plugin not found"
    }

    def "can be applied to library"() {
        given:
        prepProject()
        when:
        p.apply plugin: 'com.android.library'
        prepPlugin()
        then:
        p.plugins.hasPlugin('io.apptik.tarator-android')
    }

    def "can be applied to application"() {
        given:
        prepProject()
        when:
        p.apply plugin: 'com.android.application'
        prepPlugin()
        then:
        p.plugins.hasPlugin('io.apptik.tarator-android')
    }

    def "has task to check gherkin"() {
        given:
        prepProject()
        when:
        p.apply plugin: 'com.android.application'
        prepPlugin()
        then:
        p.tasks."checkGherkin" instanceof CheckGherkinTask
    }

    def "has task to run cucumber tests on Android device"() {
        given:
        prepProject()
        when:
        p.apply plugin: 'com.android.application'
        prepPlugin()
        then:
        p.tasks."deviceCucTest" instanceof DeviceCucTestTask
    }

}