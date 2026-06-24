import { defineConfig } from 'vitest/config'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',
  },
  resolve: {
    alias: {
      // Fix for react-transition-group ES module directory import error in MUI
      'react-transition-group/TransitionGroupContext': 'react-transition-group/cjs/TransitionGroupContext.js',
      'react-transition-group/Transition': 'react-transition-group/cjs/Transition.js',
      'react-transition-group/CSSTransition': 'react-transition-group/cjs/CSSTransition.js',
    },
  },
  ssr: {
    noExternal: ['@mui/material', '@mui/icons-material'],
  },
})
