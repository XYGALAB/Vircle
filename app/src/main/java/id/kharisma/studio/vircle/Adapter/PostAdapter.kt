package id.kharisma.studio.vircle.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import id.kharisma.studio.vircle.Model.Post
import id.kharisma.studio.vircle.Model.User
import id.kharisma.studio.vircle.R
import id.kharisma.studio.vircle.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_accountsetting.*

class PostAdapter
    (private  val mContext: Context,
     private val mPost: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>()
{
    private var firebaseUser: FirebaseUser? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.posts_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       firebaseUser = FirebaseAuth.getInstance().currentUser
        val post = mPost[position]
        Picasso.get().load(post.getPostImage()).into(holder.postImage)
        publisherInfo(holder.profileImage, holder.userName, holder.publisher, post.getPublisher())
        
    }

    override fun getItemCount(): Int {
        return mPost.size
    }

    private fun publisherInfo(profileImage: CircleImageView, userName: TextView, publisher: TextView, publisherID: String) {
        val userRef = FirebaseDatabase.getInstance("https://vircle-77b59-default-rtdb.firebaseio.com/").reference.child("Users").child(publisherID)
        userRef.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user = snapshot.getValue<User>(User::class.java)
                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(profileImage)
                    userName.text = user!!.getUsername()
                    publisher.text = user!!.getUsername()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var profileImage: CircleImageView
        var postImage: ImageView
        var likebutton: ImageView
        var commentbutton: ImageView
        var savebutton: ImageView
        var userName: TextView
        var likes: TextView
        var publisher: TextView
        var description: TextView
        var comments: TextView

        init{
            profileImage = itemView.findViewById(R.id.user_profile_image_home)
            postImage = itemView.findViewById(R.id.post_image_home)
            likebutton = itemView.findViewById(R.id.post_image_like_btn)
            commentbutton = itemView.findViewById(R.id.post_image_comment_btn)
            savebutton = itemView.findViewById(R.id.post_save_comment_btn)
            userName = itemView.findViewById(R.id.user_name_home)
            likes = itemView.findViewById(R.id.likes)
            publisher = itemView.findViewById(R.id.publisher)
            description = itemView.findViewById(R.id.description)
            comments = itemView.findViewById(R.id.comments)
        }
    }

}
