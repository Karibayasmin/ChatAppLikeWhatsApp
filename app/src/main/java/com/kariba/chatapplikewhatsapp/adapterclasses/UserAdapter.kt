package com.kariba.chatapplikewhatsapp.adapterclasses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kariba.chatapplikewhatsapp.R
import com.kariba.chatapplikewhatsapp.modelclasses.Users
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Kariba Yasmin on 9/6/21.
 */
class UserAdapter(
    mContext: Context,
    mUsers: List<Users>,
    isChatCheckBox: Boolean
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val mContext: Context
    private val mUsers: List<Users>
    private var isChatCheckBox: Boolean

    init {
        this.mContext = mContext
        this.mUsers = mUsers
        this.isChatCheckBox = isChatCheckBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var root = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout, parent, false)

        return UserViewHolder(root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users : Users = mUsers[position]
        holder.textViewUserName.text = users?.getUserName()
        Picasso.get().load(users?.getProfile()).placeholder(R.drawable.profile).into(holder.imageViewProfilePicture)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textViewUserName : TextView
        val imageViewProfilePicture : CircleImageView
        val imageViewOnline : CircleImageView
        val imageViewOffline : CircleImageView
        val textViewlastImage : TextView

         init{
             textViewUserName = itemView.findViewById(R.id.textView_user_name)
             imageViewProfilePicture = itemView.findViewById(R.id.imageView_profile_image)
             imageViewOnline = itemView.findViewById(R.id.imageView_online)
             imageViewOffline = itemView.findViewById(R.id.imageView_offline)
             textViewlastImage = itemView.findViewById(R.id.textView_last_message)
        }

    }
}