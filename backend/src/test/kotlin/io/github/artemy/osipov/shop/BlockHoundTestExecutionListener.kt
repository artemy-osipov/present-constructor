package io.github.artemy.osipov.shop

import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestPlan
import reactor.blockhound.BlockHound

class BlockHoundTestExecutionListener: TestExecutionListener {

    override fun testPlanExecutionStarted(testPlan: TestPlan?) {
        BlockHound.install()
    }
}