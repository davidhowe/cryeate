package com.davidhowe.cryeate

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davidhowe.cryeate.models.db.Coin
import com.davidhowe.cryeate.models.db.Properties
import com.davidhowe.cryeate.repositories.dao.CoinDao
import com.davidhowe.cryeate.repositories.dao.Database
import com.davidhowe.cryeate.repositories.dao.PropertiesDao
import com.davidhowe.cryeate.repositories.usecases.UCRepoCoin
import com.davidhowe.cryeate.repositories.usecases.UCRepoProperties
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTests {
    private lateinit var ucCoinRepo: UCRepoCoin
    private lateinit var ucRepoProperties: UCRepoProperties
    private lateinit var db: Database

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, Database::class.java).build()
        ucCoinRepo = UCRepoCoin(db)
        ucRepoProperties = UCRepoProperties(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //TESTS COIN
    @Test
    @Throws(Exception::class)
    fun testUCRepoCoin() {
        val testCoin1: Coin = Coin("testcoin1", price = 50.0)
        val testCoin2: Coin = Coin("testcoin2", price = 75.0)

        ucCoinRepo.updateCoins(
            listOf(testCoin1, testCoin2)
        ).subscribe {
            ucCoinRepo.getEntry("testcoin1").subscribe { retrievedCoin ->
                Timber.d("retrievedCoin=$retrievedCoin")
                Assert.assertEquals(retrievedCoin.id, testCoin1.id)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun testUCRepoProperties() {
        ucRepoProperties.setDefaultEntry().toSingle {
            ucRepoProperties.setLastAPIActive(5000L)
        }.map {
            ucRepoProperties.getLastAPIActive()
        }
        .map {
            Assert.assertEquals(it, 5000L)
            ucRepoProperties.setLastAPIPricesRetrieved(10000L)
        }
        .map {
            ucRepoProperties.getLastAPIPricesRetrieved()
        }
        .subscribe { result ->
            Assert.assertEquals(result, 10000L)
        }
    }
}