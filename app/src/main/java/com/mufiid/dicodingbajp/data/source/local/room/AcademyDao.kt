package com.mufiid.dicodingbajp.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mufiid.dicodingbajp.data.source.local.entity.CourseEntity
import com.mufiid.dicodingbajp.data.source.local.entity.CourseWithModule
import com.mufiid.dicodingbajp.data.source.local.entity.ModuleEntity

@Dao
interface AcademyDao {

    @Query("SELECT * FROM courseentities")
    fun getCourses(): LiveData<List<CourseEntity>>

    @Query("SELECT * FROM courseentities WHERE bookmarked = 1")
    fun getBookmarkedCourse(): LiveData<List<CourseEntity>>

    @Transaction
    @Query("SELECT * FROM courseentities WHERE courseId = :courseId")
    fun getCourseWithModuleById(courseId: String): LiveData<CourseWithModule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModules(module: List<ModuleEntity>)

    @Update
    fun updateModule(module: ModuleEntity)

    @Query("UPDATE moduleentities SET content = :content WHERE moduleId = :moduleId")
    fun updateModuleByContent(content: String, moduleId: String)
}