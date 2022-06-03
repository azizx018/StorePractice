import { render, screen } from '@testing-library/react';
import App from './App';

test('it should show the login component when not logged in', () => {
  const state = {
    token:null
  }
  const expectedText = "expextedText"
  const mock = () => <>{expectedText}</>
  render(<App _useSelector={fn => fn(state)} LoginC={mock}/>);

  expect(screen.getByText(expectedText)).toBeInTheDocument();

});

test('it should not show the login component when logged in', () => {
  const state = {
    token:'some token'
  }
  const expectedText = "expextedText"
  const mock = () => <>{expectedText}</>
  render(<App _useSelector={fn => fn(state)} LoginC={mock}/>);

  expect(screen.queryByText(expectedText)).not.toBeInTheDocument();

});
