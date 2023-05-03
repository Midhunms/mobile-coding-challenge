package audiobooks.codingchallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import audiobooks.codingchallenge.data.dto.PodCastData
import com.audiobooks.codingchallenge.databinding.PodcastListItemBinding

class PodCastAdapter(
    private val mListener: PodCastItemClickListener,
    private var podCastList: ArrayList<PodCastData>,
) : RecyclerView.Adapter<PodCastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PodcastListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.viewBinding.simpleDraweeView.setImageURI(podCastList[position].thumbnail)
        holder.viewBinding.titleText.text = podCastList[position].title
        holder.viewBinding.authorNameText.text = podCastList[position].publisher

        if(podCastList[position].isFavourite){
            holder.viewBinding.isFavouriteStatusText.text = "Favourited"
        }else{
            holder.viewBinding.isFavouriteStatusText.text = ""
        }
        holder.viewBinding.container.setOnClickListener {
            mListener.onItemSelected(podCastList[position],position)
        }
    }

    override fun getItemCount(): Int {
        return podCastList.size
    }

    inner class ViewHolder(val viewBinding: PodcastListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }

    interface PodCastItemClickListener {
        fun onItemSelected(item: PodCastData, position: Int)
    }


}