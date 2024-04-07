/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],

  daisyui: {
    logs: false,
    themes: [
      {
        dawn: {
          primary: '#f0ce5f',
          secondary: '#bbd853',
          accent: '#1d4ea9',
          neutral: '#c4d8ff',
          'base-100': '#add7ff',
          info: '#a8dadc',
          success: '#22913c',
          warning: '#ea9d34',
          error: '#d71e1e'
        }
      },
    ]
  }

}
