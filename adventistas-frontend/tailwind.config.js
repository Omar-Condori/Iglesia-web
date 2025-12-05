/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#1e40af',
        secondary: '#9333ea',
        accent: '#f59e0b',
      },
    },
  },
  plugins: [],
}