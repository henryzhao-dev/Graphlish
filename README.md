Graphlish

Graphlish is a backend-focused image dictionary system that combines image retrieval and AI-generated explanations to help users understand English vocabulary more intuitively.

The project is designed from a backend engineering perspective, focusing on API design, cloud integration, and AI-powered content generation.

⸻

Key Features
	•	Image-based vocabulary lookup
	•	Supports cold-start and warm-start queries
	•	Images are retrieved from a third-party image API and stored in cloud object storage
	•	Cloud-based image storage
	•	Uses Cloudflare R2 for persistent image storage
	•	Backend returns directly accessible image URLs for frontend consumption
	•	AI-powered explanations
	•	Automatically generates word explanations and example sentences using external AI APIs
	•	Supports simplified re-explanations based on user feedback when the initial explanation is unclear
	•	RESTful backend APIs
	•	Designed for easy frontend integration
	•	Clean separation between image retrieval and AI explanation logic

⸻

System Overview

When a user queries a word:
	1.	The backend first checks the database for existing records
	2.	If the word does not exist:
	•	Images are retrieved from a third-party image service
	•	Images are uploaded to cloud storage (Cloudflare R2)
	•	AI services are invoked to generate explanations and example sentences
	3.	The processed data (image URLs, explanations, examples) is stored and returned via REST APIs
	4.	If the word already exists, cached results are returned directly (warm start)

⸻

Tech Stack
	•	Backend: Java, Spring Boot
	•	Database: MySQL
	•	Cloud Storage: Cloudflare R2
	•	AI Integration: External AI APIs
	•	Architecture: RESTful APIs, layered backend design

## Configuration

This project uses environment variables to manage sensitive credentials.

Required environment variables:

- R2_ENDPOINT
- R2_ACCESS_KEY
- R2_SECRET_KEY
- R2_PUBLIC_DOMAIN
- UNSPLASH_ACCESS_KEY
- ZHIPU_API_KEY

Example:

```bash
export R2_ENDPOINT=...
export R2_ACCESS_KEY=...
export R2_SECRET_KEY=...
export R2_PUBLIC_DOMAIN=...

export UNSPLASH_ACCESS_KEY=...
export ZHIPU_API_KEY=...
