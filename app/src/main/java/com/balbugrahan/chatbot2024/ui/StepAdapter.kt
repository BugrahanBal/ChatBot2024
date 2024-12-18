package com.balbugrahan.chatbot2024.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.balbugrahan.chatbot2024.data.model.ButtonAction
import com.balbugrahan.chatbot2024.data.model.Content
import com.balbugrahan.chatbot2024.data.model.Step
import com.balbugrahan.chatbot2024.databinding.ItemButtonBinding
import com.balbugrahan.chatbot2024.databinding.ItemImageBinding
import com.balbugrahan.chatbot2024.databinding.ItemTextBinding
import com.bumptech.glide.Glide

class StepAdapter(
    private val buttonClickListener: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val steps: MutableList<Step> = mutableListOf()

    fun addUserMessage(content: String) {
        val userStep = Step(
            step = "user_message",
            type = "text",
            content = Content(text = content),
            action = ""
        )
        steps.add(userStep)
        notifyItemInserted(steps.size - 1)
    }

    fun addStep(newStep: Step) {
        steps.add(newStep)
        notifyItemInserted(steps.size - 1)
    }

    override fun getItemCount() = steps.size

    override fun getItemViewType(position: Int): Int {
        return when (steps[position].type) {
            "text" -> 0
            "button" -> 1
            "image" -> 2
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TextViewHolder(ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            1 -> ButtonViewHolder(ItemButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            2 -> ImageViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val step = steps[position]
        when (holder) {
            is TextViewHolder -> holder.bind(step.content.text)
            is ButtonViewHolder -> holder.bind(step.content.buttons!!, buttonClickListener)
            is ImageViewHolder -> holder.bind(step.content.text)
        }
    }

    inner class TextViewHolder(private val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.textView.text = text
        }
    }

    inner class ButtonViewHolder(private val binding: ItemButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(buttons: List<ButtonAction>, clickListener: (String) -> Unit) {
            binding.buttonContainer.removeAllViews()
            buttons.forEach { buttonAction ->
                val button = Button(binding.root.context).apply {
                    text = buttonAction.label
                    setOnClickListener {
                        clickListener(buttonAction.action)
                        addUserMessage(buttonAction.label)
                    }
                }
                binding.buttonContainer.addView(button)
            }
        }
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.imageView.context).load(imageUrl).into(binding.imageView)
        }
    }
}
