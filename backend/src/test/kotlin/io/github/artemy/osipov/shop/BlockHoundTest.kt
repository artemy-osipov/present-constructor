package io.github.artemy.osipov.shop

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.condition.DisabledInNativeImage
import reactor.blockhound.BlockingOperationError
import reactor.core.scheduler.Schedulers
import java.util.concurrent.ExecutionException
import java.util.concurrent.FutureTask

@DisabledInNativeImage
class BlockHoundTest {

    @Test
    fun `should detect blocked threads`() {
        val task = FutureTask {
            Thread.sleep(0)
        }
        Schedulers.parallel().schedule(task)

        val ex = assertThrows<ExecutionException> {
            task.get()
        }

        assert(ex.cause is BlockingOperationError)
    }
}