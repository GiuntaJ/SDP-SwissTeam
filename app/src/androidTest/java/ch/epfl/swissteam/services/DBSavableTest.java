package ch.epfl.swissteam.services;

import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.swissteam.services.models.DBSavable;
import ch.epfl.swissteam.services.providers.DBUtility;
import ch.epfl.swissteam.services.utils.Utils;

@RunWith(AndroidJUnit4.class)
public class DBSavableTest {
    private class TestSavable implements DBSavable {

        @Override
        public void addToDB(DatabaseReference databaseReference) {
        }
    }

    @Test(expected = Utils.IllegalCallException.class)
    public void removeFromDbThrowsIfNotImplemented() throws Utils.IllegalCallException {
        (new TestSavable()).removeFromDB(null, null);
    }

    @Test(expected = Utils.IllegalCallException.class)
    public void removeFromDbWithChildNonNullThrowsWhenRemoveFromDbNotImpl() throws Utils.IllegalCallException {
        (new TestSavable()).removeFromDB(DBUtility.get().getDb_(), "test");
    }

    @Test(expected = Utils.IllegalCallException.class)
    public void removeFromDbWithChildNullThrowsWhenRemoveFromDbNotImpl() throws Utils.IllegalCallException {
        (new TestSavable()).removeFromDB(null, null);
    }
}
