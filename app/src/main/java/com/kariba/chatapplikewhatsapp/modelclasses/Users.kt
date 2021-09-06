package com.kariba.chatapplikewhatsapp.modelclasses

/**
 * Created by Kariba Yasmin on 9/6/21.
 */
class Users {

    private var cover : String? = ""
    private var facebook : String? = ""
    private var instagram : String? = ""
    private var profile : String? = ""
    private var search : String? = ""
    private var status : String? = ""
    private var uid : String? = ""
    private var username : String? = ""
    private var website : String? = ""

    constructor()

    constructor(
        cover: String?,
        facebook: String?,
        instagram: String?,
        profile: String?,
        search: String?,
        status: String?,
        uid: String?,
        username: String?,
        website: String?
    ) {
        this.cover = cover
        this.facebook = facebook
        this.instagram = instagram
        this.profile = profile
        this.search = search
        this.status = status
        this.uid = uid
        this.username = username
        this.website = website
    }

    fun getCover() : String?{
        return cover
    }

    fun setCover(cover : String){
        this.cover = cover
    }

    fun getFacebook() : String?{
        return facebook
    }

    fun setFacebook(facebook : String){
        this.facebook = facebook
    }

    fun getInstagram() : String?{
        return instagram
    }

    fun setInstagram(instagram : String){
        this.instagram = instagram
    }

    fun getProfile() : String?{
        return profile
    }

    fun setProfile(profile : String){
        this.profile = profile
    }

    fun getSearch() : String?{
        return search
    }

    fun setSearch(search : String){
        this.search = search
    }

    fun getStatus() : String?{
        return status
    }

    fun setStatus(status : String){
        this.status = status
    }

    fun getUid() : String?{
        return uid
    }

    fun setUid(uid : String){
        this.uid = uid
    }

    fun getUserName() : String?{
        return username
    }

    fun setUserName(userName : String){
        this.username = userName
    }

    fun getWebsite() : String?{
        return cover
    }

    fun setWebsite(website : String){
        this.website = website
    }


}