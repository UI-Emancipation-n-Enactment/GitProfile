package demo.gitprofiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import demo.gitprofiles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

    }
}