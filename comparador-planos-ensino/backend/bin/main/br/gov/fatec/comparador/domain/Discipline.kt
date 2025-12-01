package br.gov.fatec.comparador.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "disciplines")
class Discipline(
    @Column(nullable = false)
    var title: String,

    @Column(length = 120, unique = false)
    var code: String? = null,

    @Column(length = 4000)
    var syllabus: String? = null,

    @Column(length = 4000)
    var complementaryContent: String? = null,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "discipline_keywords", joinColumns = [JoinColumn(name = "discipline_id")])
    @Column(name = "keyword", length = 120)
    var keywords: MutableSet<String> = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    var course: Course? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) {
    fun updateContent(
        newTitle: String,
        newCode: String?,
        newSyllabus: String?,
        newComplementaryContent: String?,
        newKeywords: Collection<String>
    ) {
        title = newTitle
        code = newCode
        syllabus = newSyllabus
        complementaryContent = newComplementaryContent
        keywords = newKeywords
            .mapNotNull { keyword ->
                val normalized = keyword.trim().lowercase()
                normalized.ifEmpty { null }
            }
            .toMutableSet()
    }
}
