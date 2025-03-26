package com.project.travelbuddy.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.travelbuddy.R
import com.project.travelbuddy.databinding.FragmentAddPostBinding
import com.project.travelbuddy.model.TravelPost

class AddPostFragment : Fragment(R.layout.fragment_add_post) {

    private lateinit var binding: FragmentAddPostBinding
    private lateinit var storageRef: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var imageUri: Uri? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPostBinding.bind(view)

        storageRef = FirebaseStorage.getInstance().reference.child("posts")
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.btnPickImage.setOnClickListener { pickImage() }
        binding.btnSubmitPost.setOnClickListener { uploadPost() }
        binding.btnPickLocation.setOnClickListener {
            val locationPickerIntent = Intent(context, LocationPickerActivity::class.java)
            startActivityForResult(locationPickerIntent, LOCATION_PICK_REQUEST)
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            binding.ivPostImage.setImageURI(imageUri)
        }
        if (requestCode == LOCATION_PICK_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            latitude = data.getDoubleExtra("latitude", 0.0)
            longitude = data.getDoubleExtra("longitude", 0.0)
            binding.etLocation.setText("Lat: $latitude, Lng: $longitude")
        }
    }

    private fun uploadPost() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()

        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || latitude == null || longitude == null || imageUri == null) {
            Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show()
            return
        }

        val imageRef = storageRef.child("${System.currentTimeMillis()}.jpg")
        imageRef.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    savePost(title, description, location, uri.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Image upload failed!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun savePost(title: String, description: String, location: String, imageUrl: String) {
        val postId = firestore.collection("posts").document().id
        val post = TravelPost(
            id = postId,
            title = title,
            description = description,
            location = location,
            imageUrl = imageUrl,
            latitude = latitude ?: 0.0,
            longitude = longitude ?: 0.0,
            authorId = auth.currentUser?.uid ?: ""
        )

        firestore.collection("posts").document(postId)
            .set(post)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Post added successfully!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addPostFragment_to_homeFragment)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to add post!", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val LOCATION_PICK_REQUEST = 2
    }
}
