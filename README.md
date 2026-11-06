<h1>💡 Overview </h1>
<h2>Playwright Java TestNG Project: Restful Booker Platform</h2>

<h3><br>This Automation Framework use to test the Web UI and API on Restful Booker Platform to let the end users to select the room and book their dates</br></h3>

<h1>👩‍💻 Tech Stack </h1>

Playwright: is an open-source browser automation and end-to-end testing framework developed by Microsoft 

Playwright crx or codegen: directly into your normal browser window. Developed by Rui Figueira, it allows you to record browser actions, pick locators, and generate test scripts without needing to launch a separate command-line inspector.

Github Copilot: is an AI-powered coding assistant and pair programmer used to help developers write, understand, test, and ship software.

Maven: A Build Management Tool

Java: Programming Language used to Design this scalable Automation Framework.

Github Actions: Automate, customize, and execute your software development workflows right in your repository with GitHub Actions

TestNG Test Automation Framework: an open-source automated testing framework for the Java programming language

Postman API: a leading collaborative platform for building, executing, and automating API testing.

SQL Server Management Studio (SSMS): The testing framework instantly queries the target database table to check if the specific schema constraints, values, and foreign keys match the payload input.

Allure Report: is an open-source framework-agnostic test result visualization tool. It transforms test execution data into clear, interactive HTML reports. It works with popular testing frameworks across JavaScript, Python, Java, C#, PHP, and Ruby, helping teams track test results and better manage software quality.

<h1> 📦 Getting Started </h1>

To get a local copy of this project up and running, follow these steps.

<h1> ### 🚀 Prerequisites </h1>

- **Node.js** (v16.x or higher) and **npm** or **yarn**.
- **Npm**: If you prefer using npm for package management and running scripts.
- **PostgreSQL** (or another supported SQL database).

<h1> ## 🛠️ Installation </h1>

1. **Clone the repository:**

   ```bash
   git clone https://github.com/mo-hassann/nextjs-quran-app.git
   cd nextjs-quran-app
   ```

2. **Install dependencies:**

   Using Npm:

   ```bash
   npm install
   ```

3. **Set up environment variables:**

   Create a `.env` file in the root directory and add the following variables:

   ```env
   NEXT_PUBLIC_APP_URL=http://localhost:3000

   #database
   DATABASE_URL=your_database_url
   DATABASE_SECRET=your_database_secret
   DRIZZLE_DATABASE_URL=your_database_url_for_drizzle

   #auth
   AUTH_SECRET=any_random_secret
   ```

4. **Run database migrations:**

   Ensure your database is running and then run:

   ```bash
   npm run drizzle-kit migrate
   ```

5. **Start the development server:**

   ```bash
   npm run dev
   ```

   Open [http://localhost:3000](http://localhost:3000) to view the app in your browser.

## 📖 Usage

### ✔ Running the app

- **Development mode:** `npm run dev`, `yarn dev`, or `bun dev`.
- **Production mode:** `npm run build && npm start`, `yarn build && yarn start`, or `bun run build && bun start`.


