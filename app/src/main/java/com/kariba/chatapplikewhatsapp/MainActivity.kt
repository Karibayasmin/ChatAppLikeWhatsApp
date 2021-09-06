package com.kariba.chatapplikewhatsapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.kariba.chatapplikewhatsapp.fragments.ChatsFragment
import com.kariba.chatapplikewhatsapp.fragments.SearchFragment
import com.kariba.chatapplikewhatsapp.fragments.SettingsFragment
import com.kariba.chatapplikewhatsapp.modelclasses.Users
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var refUsers : DatabaseReference? = null
    var firebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))

        firebaseUser =  FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser?.uid ?: "")

        // Instead of default toolbar we want to show customize one, that's why set the actionbar title empty.
        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        var tabLayout : TabLayout = findViewById(R.id.tabLayout_main)

        var viewPager : ViewPager = findViewById(R.id.viewPager)

        var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(ChatsFragment(), "Chats")
        viewPagerAdapter.addFragment(SearchFragment(), "Search")
        viewPagerAdapter.addFragment(SettingsFragment(), "Settings")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        //display username and profile picture
        refUsers?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val users : Users? = snapshot.getValue(Users::class.java)

                    textView_userName.text = users?.getUserName()
                    Picasso.get().load(users?.getProfile()).placeholder(R.drawable.profile).into(imageView_profile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId)
        {
            R.id.action_logout ->
            {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

                return true
            }
        }

        return false
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager)
    {
        private val fragments : ArrayList<Fragment>
        private val titles : ArrayList<String>

        init {
            fragments = ArrayList<Fragment>()
            titles = ArrayList<String>()
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment, title : String)
        {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }
}