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
          primary: '#e63946',
          secondary: '#1d3557',
          accent: '#457b9d',
          neutral: '#f2e9e1',
          'base-100': '#f3f3f3',
          info: '#a8dadc',
          success: '#22913c',
          warning: '#ea9d34',
          error: '#d71e1e'
        }
      },
    ]
  }

}
