package com.elixirgym.elixir.data

import com.elixirgym.elixir.domain.model.Trainer
import com.elixirgym.elixir.domain.model.TrainerComment

object SampleTrainerData {
    fun getTrainers(): List<Trainer> {
        return listOf(
            Trainer(
                id = "1",
                name = "John Smith",
                photoUrl = "https://i.pravatar.cc/300?img=12",
                shortDescription = "Certified personal trainer with 10+ years of experience in strength training and body transformation.",
                specialization = "Personal Trainer",
                rating = 4.8f,
                comments = listOf(
                    TrainerComment(
                        id = "c1",
                        memberName = "Alex Johnson",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=1",
                        comment = "John helped me lose 20kg in 6 months. His personalized approach and motivation made all the difference!",
                        rating = 5.0f,
                        date = "2024-10-15"
                    ),
                    TrainerComment(
                        id = "c2",
                        memberName = "Maria Garcia",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=5",
                        comment = "Best trainer I've ever had. Very knowledgeable and patient.",
                        rating = 4.5f,
                        date = "2024-10-08"
                    ),
                    TrainerComment(
                        id = "c3",
                        memberName = "Chris Lee",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=8",
                        comment = "Great sessions, really pushes you to achieve your goals!",
                        rating = 5.0f,
                        date = "2024-09-22"
                    )
                )
            ),
            Trainer(
                id = "2",
                name = "Sarah Johnson",
                photoUrl = "https://i.pravatar.cc/300?img=47",
                shortDescription = "Experienced yoga instructor specializing in Vinyasa, Hatha, and meditation for mind-body wellness.",
                specialization = "Yoga Instructor",
                rating = 4.9f,
                comments = listOf(
                    TrainerComment(
                        id = "c4",
                        memberName = "Emma Wilson",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=9",
                        comment = "Sarah's yoga classes are transformative. I feel more flexible and relaxed.",
                        rating = 5.0f,
                        date = "2024-10-20"
                    ),
                    TrainerComment(
                        id = "c5",
                        memberName = "David Chen",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=13",
                        comment = "Very calming and professional. Perfect for beginners!",
                        rating = 4.8f,
                        date = "2024-10-12"
                    )
                )
            ),
            Trainer(
                id = "3",
                name = "Mike Williams",
                photoUrl = "https://i.pravatar.cc/300?img=33",
                shortDescription = "Elite strength and conditioning coach focused on powerlifting and athletic performance.",
                specialization = "Strength Coach",
                rating = 4.7f,
                comments = listOf(
                    TrainerComment(
                        id = "c6",
                        memberName = "Robert Taylor",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=14",
                        comment = "Mike knows his stuff! My deadlift has improved by 50kg in 3 months.",
                        rating = 5.0f,
                        date = "2024-10-18"
                    ),
                    TrainerComment(
                        id = "c7",
                        memberName = "Lisa Anderson",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=10",
                        comment = "Intense workouts but great results. Highly recommend!",
                        rating = 4.5f,
                        date = "2024-10-05"
                    )
                )
            ),
            Trainer(
                id = "4",
                name = "Emily Davis",
                photoUrl = "https://i.pravatar.cc/300?img=45",
                shortDescription = "CrossFit Level 3 coach with expertise in functional fitness and HIIT training.",
                specialization = "CrossFit Coach",
                rating = 4.6f,
                comments = listOf(
                    TrainerComment(
                        id = "c8",
                        memberName = "James Martinez",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=15",
                        comment = "Emily's CrossFit classes are challenging but fun. Great community!",
                        rating = 4.5f,
                        date = "2024-10-10"
                    ),
                    TrainerComment(
                        id = "c9",
                        memberName = "Sophie Brown",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=16",
                        comment = "Very energetic and motivating trainer!",
                        rating = 4.8f,
                        date = "2024-09-28"
                    )
                )
            ),
            Trainer(
                id = "5",
                name = "David Brown",
                photoUrl = "https://i.pravatar.cc/300?img=51",
                shortDescription = "Professional swimming instructor with Olympic coaching certification for all skill levels.",
                specialization = "Swimming Instructor",
                rating = 4.9f,
                comments = listOf(
                    TrainerComment(
                        id = "c10",
                        memberName = "Oliver White",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=17",
                        comment = "David taught my kids to swim. Patient and excellent with children!",
                        rating = 5.0f,
                        date = "2024-10-22"
                    ),
                    TrainerComment(
                        id = "c11",
                        memberName = "Isabella Thomas",
                        memberPhotoUrl = "https://i.pravatar.cc/150?img=20",
                        comment = "Improved my technique significantly. Highly professional!",
                        rating = 4.8f,
                        date = "2024-10-14"
                    )
                )
            )
        )
    }
}
