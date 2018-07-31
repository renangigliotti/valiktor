package org.valiktor.springframework.boot.autoconfigure

import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
import org.springframework.boot.test.util.EnvironmentTestUtils
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.valiktor.springframework.config.ValiktorConfiguration
import kotlin.test.*

class ValiktorAutoConfigurationTest {

    private var context = AnnotationConfigApplicationContext()

    @BeforeTest
    fun setUp() {
        this.context = AnnotationConfigApplicationContext()
    }

    @AfterTest
    fun tearDown() {
        this.context.close()
    }

    @Test
    fun `should create with default properties`() {
        registerAndRefresh(listOf(
                ValiktorAutoConfiguration::class.java,
                PropertyPlaceholderAutoConfiguration::class.java))

        val config = this.context.getBean(ValiktorConfiguration::class.java)
        assertNull(config.baseBundleName)
    }

    @Test
    fun `should create with custom properties`() {
        EnvironmentTestUtils.addEnvironment(this.context, "valiktor.baseBundleName:test")

        registerAndRefresh(listOf(
                ValiktorAutoConfiguration::class.java,
                PropertyPlaceholderAutoConfiguration::class.java))

        val config = this.context.getBean(ValiktorConfiguration::class.java)
        assertEquals(config.baseBundleName, "test")
    }

    @Test
    fun `should create with custom bean`() {
        EnvironmentTestUtils.addEnvironment(this.context, "valiktor.baseBundleName:test")

        registerAndRefresh(listOf(
                ValiktorConfigurationFactory::class.java,
                ValiktorAutoConfiguration::class.java,
                PropertyPlaceholderAutoConfiguration::class.java))

        val config = this.context.getBean(ValiktorConfiguration::class.java)
        assertEquals(config.baseBundleName, "testMessages")
    }

    private fun registerAndRefresh(annotatedClasses: List<Class<*>>) {
        annotatedClasses.forEach { this.context.register(it) }
        this.context.refresh()
    }
}

@Configuration
class ValiktorConfigurationFactory {

    @Bean
    fun valiktorConfiguration(): ValiktorConfiguration =
            ValiktorConfiguration(baseBundleName = "testMessages")
}