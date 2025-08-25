import com.example.wordsolve.ShopItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopItemTest
{

    @Test
    public void testGetName()
    {
        // test
        Assertions.fail("for testing");

        ShopItem shopItem1 = new ShopItem("double", 1, 0, 1);
        assertEquals("double", shopItem1.getName());
    }
}
