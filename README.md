# GitHub Activity CLI

## Description
This CLI application fetches GitHub user activity using the GitHub API. It supports various output formats, error handling, and colored terminal output.

## Features
✅ Fetch GitHub user activity
✅ Process and display data in a user-friendly format
✅ Handle errors (404, 403, 500)
✅ Support for colored terminal output (ASCII codes)
✅ Request timeout handling
✅ Output in JSON/table format

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/github-activity-cli.git
   cd github-activity-cli
   ```
2. Build the project using Maven or Gradle:
   ```sh
   mvn package
   ```

## Usage
Running without parameters will display available commands:
```sh
java -jar github-activity-cli.jar
```

Fetch user activity:
```sh
java -jar github-activity-cli.jar --user octocat
```

Output in JSON format:
```sh
java -jar github-activity-cli.jar --user octocat --format json
```

## Error Handling
| Code | Description |
|------|-------------------------------------------|
| 404  | User not found |
| 403  | Rate limit exceeded or access forbidden |
| 500  | GitHub internal server error |
| 422  | Invalid request |

## API Response Example
```json
[
  {
    "type": "WatchEvent",
    "actor": {
      "login": "octocat"
    },
    "repo": {
      "name": "octocat/Hello-World"
    },
    "created_at": "2022-06-09T12:47:28Z"
  }
]
```

## Simulating Errors
- **403 Forbidden** – Execute 60+ requests without authentication or use an invalid token.
- **404 Not Found** – Request a non-existent user (`java -jar github-activity-cli.jar --user unknown_user`).
- **500 Internal Server Error** – Use an intentionally incorrect URL.

## Contact
Author: Bohdan Bedrii  
GitHub: https://github.com/bbed1989



https://roadmap.sh/projects/github-user-activity
