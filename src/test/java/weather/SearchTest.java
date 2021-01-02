package test.java.weather;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.abstracts.AbstractProjectTest;
import test.java.fragment.FindPage;
import test.java.fragment.FindPage.Record;
import test.java.fragment.SearchForm;

public class SearchTest extends AbstractProjectTest {

    @Test(dependsOnGroups = "createProject")
    public void searchDesignTest() {
        SearchForm searchForm = initHomePage().getHeaderWebsite().getSearchForm();
        assertTrue(searchForm.isHiddenSubmit(), "Submit button should be hidden");
        assertTrue(searchForm.isSearchInputVisible(), "Search input should be visible");
        assertEquals(searchForm.getPlaceHolder(), "Weather in your city");
    }

    //should design independence test; however, want to show how to control test;
    @Test(dependsOnMethods = "searchDesignTest", description = "search with single & multiple results")
    public void searchTestWithResult() {
        SearchForm searchFormOnHeader = initHomePage().getHeaderWebsite().getSearchForm();
        searchFormOnHeader.search("usa, JP");
        FindPage findPage = FindPage.getInstance(browser);
        assertEquals(findPage.getRecordSize(), 1);
        Record record = findPage.getRecordByTitle("Usa, JP");
        //Stable data
        assertEquals(record.getLocation(), "Usa, JP");
        assertEquals(record.getPosition(), "[33.45, 133.45]");
        //Unstable data
        assertTrue(record.getImageUrl().contains("http://openweathermap.org/img/wn/"), "Wrong path");
        assertTrue(record.getBadge().matches("\\d°С"), "Should be right format");
        assertTrue(record.hasWeather(), "Weather should be visible");

        searchFormOnHeader.search("usa");
        assertEquals(findPage.getRecordSize(), 3);

        searchFormOnHeader.search("   usa, JP");
        record = findPage.getRecordByTitle("Usa, JP");
        assertEquals(record.getLocation(), "Usa, JP");
        assertEquals(record.getPosition(), "[33.45, 133.45]");
        assertTrue(record.getImageUrl().contains("http://openweathermap.org/img/wn/"), "Wrong path");
        assertTrue(record.getBadge().matches("\\d°С"), "Should be right format");
        assertTrue(record.hasWeather(), "Weather should be visible");
    }

    @DataProvider(name = "input")
    public Object[][] getInput() {
        return new Object[][]{
                {"abc"},
                {"*, JP"}
        };
    }

    @Test(dependsOnMethods = "searchDesignTest", dataProvider = "input", description = "search with no found result")
    public void searchTestWithoutResult(String input) {
        SearchForm searchFormOnHeader = initHomePage().getHeaderWebsite().getSearchForm();
        searchFormOnHeader.search(input);
        FindPage findPage = FindPage.getInstance(browser);
        assertEquals(findPage.getSearchForm().getValue(),input);
        assertEquals(findPage.getAlertWarning(),"Not found");
    }

    @Test(dependsOnMethods = "searchDesignTest", description = "search with range of length input")
    public void limitInputSearchTest() {
        SearchForm searchFormOnHeader = initHomePage().getHeaderWebsite().getSearchForm();
        searchFormOnHeader.search("");
        FindPage findPage = FindPage.getInstance(browser);
        assertEquals(findPage.getSearchForm().getValue(),"London, UK");

        searchFormOnHeader.search("ab");
        assertFalse(findPage.isAlertWarningVisible(),"Shouldn't display warning");
        assertFalse(findPage.isRecordTableVisible(),"Shouldn't display record table");

        searchFormOnHeader.search("abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdea");
        assertFalse(findPage.isAlertWarningVisible(),"Shouldn't display warning");
        assertFalse(findPage.isRecordTableVisible(),"Shouldn't display record table");
    }
}
