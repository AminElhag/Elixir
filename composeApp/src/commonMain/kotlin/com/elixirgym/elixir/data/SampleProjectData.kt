package com.elixirgym.elixir.data

import com.elixirgym.elixir.domain.model.Project
import com.elixirgym.elixir.domain.model.ProjectCategory

object SampleProjectData {
    fun getProjects(): List<Project> {
        return listOf(
            Project(
                id = "proj_001",
                name = "PT Pilates - 24 Classes",
                description = "Premium Pilates training package with personalized attention. Perfect for improving core strength, flexibility, and posture. Includes 24 one-on-one sessions with certified Pilates instructors.",
                imageUrl = "https://images.unsplash.com/photo-1518611012118-696072aa579a?w=800",
                price = 6072.00,
                currency = "SAR",
                numberOfClasses = 24,
                category = ProjectCategory.PILATES,
                trainer = "Expert Pilates Instructor",
                durationPerClass = 60,
                validityPeriod = 90,
                features = listOf(
                    "24 one-on-one sessions",
                    "Personalized training plan",
                    "Progress tracking",
                    "Equipment included",
                    "Valid for 90 days"
                )
            ),
            Project(
                id = "proj_002",
                name = "Yoga Mastery - 16 Classes",
                description = "Comprehensive yoga program for all levels. Enhance flexibility, reduce stress, and find inner peace through guided yoga sessions.",
                imageUrl = "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=800",
                price = 3200.00,
                currency = "SAR",
                numberOfClasses = 16,
                category = ProjectCategory.YOGA,
                trainer = "Certified Yoga Master",
                durationPerClass = 75,
                validityPeriod = 60,
                features = listOf(
                    "16 guided sessions",
                    "All experience levels welcome",
                    "Breathing techniques",
                    "Meditation guidance",
                    "Valid for 60 days"
                )
            ),
            Project(
                id = "proj_003",
                name = "Personal Training - 12 Sessions",
                description = "Intensive personal training program tailored to your fitness goals. Work one-on-one with expert trainers to achieve remarkable results.",
                imageUrl = "https://images.unsplash.com/photo-1571019614242-c5c5dee9f50b?w=800",
                price = 4500.00,
                currency = "SAR",
                numberOfClasses = 12,
                category = ProjectCategory.PERSONAL_TRAINING,
                trainer = "Elite Personal Trainer",
                durationPerClass = 60,
                validityPeriod = 45,
                features = listOf(
                    "12 personalized sessions",
                    "Custom workout plan",
                    "Nutrition guidance",
                    "Body composition analysis",
                    "Valid for 45 days"
                )
            ),
            Project(
                id = "proj_004",
                name = "Group Training - 20 Classes",
                description = "High-energy group training sessions that combine motivation, community, and effective workouts. Perfect for those who thrive in a group setting.",
                imageUrl = "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?w=800",
                price = 2800.00,
                currency = "SAR",
                numberOfClasses = 20,
                category = ProjectCategory.GROUP_TRAINING,
                durationPerClass = 45,
                validityPeriod = 60,
                features = listOf(
                    "20 group sessions",
                    "Max 10 people per class",
                    "Varied workout styles",
                    "Community support",
                    "Valid for 60 days"
                )
            ),
            Project(
                id = "proj_005",
                name = "Nutrition & Wellness - 8 Sessions",
                description = "Complete nutrition and wellness program with personalized meal plans and lifestyle coaching. Transform your health from the inside out.",
                imageUrl = "https://images.unsplash.com/photo-1490645935967-10de6ba17061?w=800",
                price = 3500.00,
                currency = "SAR",
                numberOfClasses = 8,
                category = ProjectCategory.NUTRITION,
                trainer = "Certified Nutritionist",
                durationPerClass = 45,
                validityPeriod = 60,
                features = listOf(
                    "8 consultation sessions",
                    "Personalized meal plans",
                    "Supplement guidance",
                    "Lifestyle coaching",
                    "Valid for 60 days"
                )
            ),
            Project(
                id = "proj_006",
                name = "Advanced Pilates - 32 Classes",
                description = "Extended Pilates program for serious practitioners. Build exceptional core strength and body control through advanced techniques.",
                imageUrl = "https://images.unsplash.com/photo-1599901860904-17e6ed7083a0?w=800",
                price = 7680.00,
                currency = "SAR",
                numberOfClasses = 32,
                category = ProjectCategory.PILATES,
                trainer = "Master Pilates Instructor",
                durationPerClass = 60,
                validityPeriod = 120,
                features = listOf(
                    "32 advanced sessions",
                    "Progressive difficulty",
                    "Equipment mastery",
                    "Flexibility training",
                    "Valid for 120 days"
                )
            )
        )
    }

    fun getProjectById(id: String): Project? {
        return getProjects().find { it.id == id }
    }

    fun getProjectsByCategory(category: ProjectCategory): List<Project> {
        return getProjects().filter { it.category == category }
    }
}
