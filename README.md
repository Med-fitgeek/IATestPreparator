🧠 B-Ready
==========

> **B-Ready — Transforme ton savoir en maîtrise.**

B-Ready est une plateforme web de génération et de jeu de quiz intelligents à partir de contenus (cours, notes, documents, etc.), avec suivi des sessions et des résultats.Objectif : **apprendre mieux, plus vite, et avec des résultats mesurables**.

✨ Fonctionnalités
-----------------

*   🔐 Authentification sécurisée (JWT)
    
*   📚 Création de quiz à partir d’une _knowledge source_
    
*   🤖 Génération automatique de quiz via LLM
    
*   ▶️ Quiz Player pour jouer une session
    
*   🧾 Gestion des sessions utilisateur
    
*   📊 Persistance des résultats (score, réponses, erreurs, explications)
    
*   📂 Dashboard avec historique des sessions par quiz
    
*   🔁 Relancer un quiz à partir d’un même contenu
    
*   🧠 Séparation claire :
    
    *   DTO/POJO pour la génération
        
    *   Entités métier pour la persistance
        

🏗️ Architecture
----------------

### Backend

*   Java 17+
    
*   Spring Boot
    
*   Spring Security + JWT
    
*   JPA / Hibernate
    
*   PostgreSQL
    
*   Architecture en couches :
    
    *   Controller (API REST)
        
    *   Service (logique métier)
        
    *   Repository (accès données)
        
    *   DTO (échanges)
        
    *   Entities (modèle persistant)
        

### Frontend

*   Angular
    
*   Angular Router + Guards
    
*   HTTP Interceptor pour le JWT
    
*   Gestion d’état pour le quiz en cours
    
*   Composants principaux :
    
    *   Auth (Login / Register)
        
    *   Dashboard
        
    *   Knowledge Source
        
    *   Quiz Player
        
    *   Quiz Preview (roadmap)
        

🧩 Concepts clés
----------------

### Quiz généré

*   Reçu sous forme de GeneratedQuizDto
    
*   Utilisé pour afficher et jouer le quiz
    
*   **Non persisté tel quel** en base
    

### Session de quiz

*   Représente une tentative utilisateur
    
*   Persistée avec :
    
    *   Utilisateur
        
    *   Titre / signature du contenu
        
    *   Date
        
    *   Score
        
    *   Réponses utilisateur
        

### Résultats

*   Persistés :
    
    *   Réponses
        
    *   Erreurs
        
    *   Explications
        
    *   Score final
        
*   Les objets de génération restent des **objets de transport**
    

🔄 Flow fonctionnel
-------------------

1.  L’utilisateur fournit un contenu (knowledge source)
    
2.  Le backend génère un quiz via LLM → GeneratedQuizDto
    
3.  Redirection vers le **Quiz Player**
    
4.  L’utilisateur joue la session
    
5.  Fin de session :
    
    *   Envoi des résultats au backend
        
    *   Persistance de la session et des résultats
        
6.  Le dashboard affiche l’historique
    
7.  L’utilisateur peut relancer un nouveau quiz sur le même contenu
    

🔐 Sécurité
-----------

*   Authentification JWT
    
*   Token stocké côté frontend
    
*   Injection automatique via HttpInterceptor
    
*   Routes protégées via AuthGuard
    
*   CORS configuré côté backend
    

🛠️ Stack technique
-------------------

**Backend**

*   Java 17+
    
*   Spring Boot
    
*   Spring Security
    
*   JPA / Hibernate
    
*   PostgreSQL
    

**Frontend**

*   Angular
    
*   RxJS
    
*   Angular Router
    
*   Angular HTTP Client
    

🚀 Installation
---------------

### Backend

`   git clone   cd backend  ./mvnw spring-boot:run   `

Configurer :

*   Base de données
    
*   Secret JWT
    
*   Accès au provider LLM
    

### Frontend

`   cd frontend  npm install  ng serve   `

🧪 Tests
--------

*   Tests unitaires sur les services métier
    
*   Tests d’intégration sur les endpoints critiques
    
*   Validation des DTO et règles métier côté backend
    

🛣️ Roadmap
-----------

*   Quiz Preview avec bouton "Démarrer"
    
*   Statistiques avancées par utilisateur
    
*   Historique par thème / matière
    
*   Mode révision ciblée sur les erreurs
    
*   Recommandations de révision
    
*   Partage de quiz
    
*   Export des résultats
    

🤝 Contribution
---------------

Les contributions sont bienvenues.

1.  Fork le projet
    
2.  Crée une branche feature (git checkout -b feature/ma-feature)
    
3.  Commit (git commit -m "feat: ajoute ma feature")
    
4.  Push (git push origin feature/ma-feature)
    
5.  Ouvre une Pull Request
    

Merci de :

*   Respecter la structure du projet
    
*   Ajouter des tests si nécessaire
    
*   Garder un code lisible et documenté
    

📄 Licence
----------

Ce projet est sous licence MIT.Voir le fichier LICENSE pour plus de détails.

🧠 Philosophie produit
----------------------

*   Pas de gadget inutile
    
*   Focus sur l’apprentissage réel
    
*   L’IA comme **outil**, pas comme argument marketing
    
*   Mesurable, utile, efficace