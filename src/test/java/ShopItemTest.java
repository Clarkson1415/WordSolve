import com.example.wordsolve.ShopItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopItemTest
{

    @Test
    public void testGetName()
    {
        ShopItem shopItem1 = new ShopItem("double", 1, 0, 1);
        assertEquals("double", shopItem1.getName());
    }

    @Test
    public void testSetName()
    {
        ShopItem shopItem1 = new ShopItem("double", 1, 0, 1);

        shopItem1.setName("nameSet");
        assertEquals("nameSet", shopItem1.getName());
    }
}
