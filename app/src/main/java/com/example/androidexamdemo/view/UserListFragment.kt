package com.example.androidexamdemo.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.setPadding
import androidx.viewpager2.widget.ViewPager2
import com.example.androidexamdemo.R
import com.example.androidexamdemo.UserDataModel
import com.example.androidexamdemo.adapter.SlideImageAdapter
import com.example.androidexamdemo.adapter.UserAdapter
import com.example.androidexamdemo.databinding.FragmentUserListBinding
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class UserListFragment : Fragment() {
    private lateinit var userListBinding: FragmentUserListBinding
    private lateinit var slideImageAdapter: SlideImageAdapter
    private lateinit var userAdapter:UserAdapter


    val indicatorDotsArraylist= arrayListOf<TextView>()
    val imageArrayList=ArrayList<Drawable>()
    val userListArrayList= arrayListOf<UserDataModel>()
    var enterQuery:String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        userListBinding= FragmentUserListBinding.inflate(inflater,container,false)


        return userListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val i: Drawable? =requireContext().resources.getDrawable(R.drawable.egypt)

        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.tajmahal))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.rome))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.wonder))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.seven_wonder))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.egypt))

        userListArrayList.add(UserDataModel("India",resources.getDrawable(R.drawable.tajmahal),"+91","Taj Mahal"))
        userListArrayList.add(UserDataModel("Italy",resources.getDrawable(R.drawable.rome),"+91","Status Of Rome"))
        userListArrayList.add(UserDataModel("Seven Wonder Images",resources.getDrawable(R.drawable.wonder),"+91","Seven Wonder"))
        userListArrayList.add(UserDataModel("All Seven Images",resources.getDrawable(R.drawable.seven_wonder),"+91","All Seven Wonder"))
        userListArrayList.add(UserDataModel("Egypt",resources.getDrawable(R.drawable.egypt),"+91","Pyramid"))


        makeDotsIndicator()


        slideImageAdapter= SlideImageAdapter()
        slideImageAdapter.addImages(imageArrayList)
        userListBinding.viewPager.adapter=slideImageAdapter
        userListBinding.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {

                super.onPageSelected(position)
                setDotsPostion(position)
                Log.i("image","image at $position")

            }
        })
        userAdapter= UserAdapter()
        userAdapter.addUser(userListArrayList)
        userListBinding.userRv.adapter=userAdapter
      //  userListBinding.searchEdt.imeOptions=EditorInfo.IME_ACTION_DONE
        userListBinding.searchEdt.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               userListBinding.searchEdt.clearFocus()
                enterQuery=query.toString().toUpperCase().trim()
                if (userListArrayList.component1().countryName.toUpperCase().contains(enterQuery!!)) {
                    userAdapter.filter.filter(enterQuery)
                }
                else {
                    Toast.makeText(requireContext(), "no data found", Toast.LENGTH_LONG).show()
                }
            return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false

            }

        })
    }

    fun setDotsPostion(postion:Int){
        for (i in imageArrayList.indices){
            if (i==postion){
                indicatorDotsArraylist[i].setTextColor(resources.getColor(R.color.grey))
            }else{
                indicatorDotsArraylist[i].setTextColor(resources.getColor(R.color.black))
            }
        }

    }


    fun makeDotsIndicator(){
        for (i in imageArrayList.indices){
            indicatorDotsArraylist.add( TextView(requireContext()))
            indicatorDotsArraylist[i].text=Html.fromHtml("&#9679;")
            indicatorDotsArraylist[i].setPadding(2)
            indicatorDotsArraylist[i].setTextSize(18F)
            indicatorDotsArraylist[i].setTextColor(resources.getColor(R.color.black))
            userListBinding.linearLayout.addView(indicatorDotsArraylist[i])
        }
    }
}