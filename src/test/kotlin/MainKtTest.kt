import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test

internal class MainKtTest {
    @Test
    fun test() {
        assertDoesNotThrow { main() }
    }
}